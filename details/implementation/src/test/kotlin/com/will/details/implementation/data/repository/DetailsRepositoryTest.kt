package com.will.details.implementation.data.repository

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.datasource.DetailsRemoteDataSource
import com.will.details.implementation.data.model.DetailsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.samePropertyValuesAs
import org.junit.Assert.assertTrue
import org.junit.Test

internal class DetailsRepositoryTest {

    private val mockedDataSource = mockk<DetailsRemoteDataSource>()
    private val repository = DetailsRepositoryImpl(mockedDataSource)

    /*
        GIVEN the mockedDataSource.getItem return success with code 200 in body
        WHEN call repository.getItem
        THEN should call mockedDataSource.getItem one time
            AND the result should be the return of mockedDataSource.getItem
     */
    @Test
    fun validateSuccessGetItem() = runTest {
        val response = listOf(DetailsResponse(code = 200, body = null))

        coEvery { mockedDataSource.getItem(any()) } returns NetworkResponse.Success(response)

        val result = repository.getItem("1")

        coVerify(exactly = 1) { mockedDataSource.getItem("1") }

        assertThat(result.getValueOrNull(), samePropertyValuesAs(response.first()))
        assertTrue(result.isSuccess())
    }

    /*
        GIVEN the mockedDataSource.getItem return success different from 200 in body
        WHEN call repository.getItem
        THEN should call mockedDataSource.getItem one time
            AND the result should be the return of mockedDataSource.getItem
     */
    @Test
    fun validateSuccessWithErrorCodeGetItem() = runTest {
        val response = listOf(DetailsResponse(code = 999, body = null))

        coEvery { mockedDataSource.getItem(any()) } returns NetworkResponse.Success(response)

        val result = repository.getItem("1")

        coVerify(exactly = 1) { mockedDataSource.getItem("1") }

        assertThat(
            result,
            samePropertyValuesAs(NetworkResponse.Error.UnknownError(code = 999, message = ""))
        )
    }

    /*
        GIVEN the mockedDataSource.getItem return NetworkResponse.Error
        WHEN call repository.getItem
        THEN should call mockedDataSource.getItem one time
            AND the result should be the return of mockedDataSource.getItem
     */
    @Test
    fun validateErrorGetItem() = runTest {
        val response = NetworkResponse.Error.UnknownError(code = 500, message = "")

        coEvery { mockedDataSource.getItem(any()) } returns response

        val result = repository.getItem("1")

        coVerify(exactly = 1) { mockedDataSource.getItem("1") }

        assertThat(result, samePropertyValuesAs(response))
    }
}
