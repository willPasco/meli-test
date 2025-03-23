package com.will.details.implementation.data.datasource

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.api.DetailsService
import com.will.details.implementation.data.model.DetailsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

internal class DetailsRemoteDataSourceTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val mockedService = mockk<DetailsService>()
    private val dataSource = DetailsRemoteDataSourceImpl(
        dispatcher = testDispatcher,
        detailsService = mockedService,
    )

    /*
        WHEN call dataSource.getItem
        THEN should call mockedService.getItem one time
            AND the result should be the return of mockedService.getItem
     */
    @Test
    fun validateGetItem() = testScope.runTest {
        val response = NetworkResponse.Success(listOf(DetailsResponse(code = null, body = null)))

        coEvery { mockedService.getItem(any()) } returns response

        val result = dataSource.getItem("1")

        coVerify(exactly = 1) { mockedService.getItem("1") }

        assertThat(response, equalTo(result))
    }
}