package com.will.details.implementation.data.repository

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.datasource.DetailsRemoteDataSource
import com.will.details.implementation.data.model.DetailsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

internal class DetailsRepositoryTest {

    private val mockedDataSource = mockk<DetailsRemoteDataSource>()
    private val repository = DetailsRepositoryImpl(mockedDataSource)

    /*
        WHEN call repository.getItem
        THEN should call mockedDataSource.getItem one time
            AND the result should be the return of mockedDataSource.getItem
     */
    @Test
    fun validateGetItem() = runTest {
        val response = NetworkResponse.Success(listOf(DetailsResponse(code = null, body = null)))

        coEvery { mockedDataSource.getItem(any()) } returns response

        val result = repository.getItem("1")

        coVerify(exactly = 1) { mockedDataSource.getItem("1") }

        assertThat(response, equalTo(result))
    }
}
