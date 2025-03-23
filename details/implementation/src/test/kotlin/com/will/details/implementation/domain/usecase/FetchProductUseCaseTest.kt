package com.will.details.implementation.domain.usecase

import com.will.core.network.api.model.NetworkResponse
import com.will.details.implementation.data.model.DetailsBodyResponse
import com.will.details.implementation.data.model.DetailsResponse
import com.will.details.implementation.data.repository.DetailsRepository
import com.will.details.implementation.domain.model.ProductDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class FetchProductUseCaseTest {

    private val mockedRepository = mockk<DetailsRepository>()
    private val mockedMapper = mockk<ProductDetailsMapper>()
    private val useCase = FetchProductUseCaseImpl(
        detailsRepository = mockedRepository, mapper = mockedMapper
    )
    private val expected = ProductDetails(
        imageList = listOf(),
        title = "",
        price = 0.0,
        discount = null,
        badges = listOf(),
        description = null,
        attributes = null
    )

    @Before
    fun setup() {
        every { mockedMapper.map(any()) } returns expected
    }

    /*
        GIVEN the mockedRepository.getItem returns NetworkResponse.Success
        WHEN call useCase.execute\
        THEN should call mockedRepository.getItem
            AND call mockedMapper.map
            AND return a Result.Success with a product detail model returned by mockedMapper.map
     */
    @Test
    fun validateSuccessResponse() = runTest {
            coEvery {
                mockedRepository.getItem(any())
            } returns NetworkResponse.Success(
                listOf(
                    DetailsResponse(
                        code = null, body = DetailsBodyResponse(
                            title = null,
                            price = null,
                            originalPrice = null,
                            warranty = null,
                            description = null,
                            pictures = listOf(),
                            attributes = listOf()
                        )
                    )
                )
            )

        val result = useCase.execute("1")

        coVerifyOrder {
            mockedRepository.getItem("1")
            mockedMapper.map(any())
        }

        assertThat(result, equalTo(Result.success(expected)))
    }

    /*
    GIVEN the mockedRepository.getItem returns NetworkResponse.Error
    WHEN call useCase.execute
    THEN should call mockedRepository.getItem
        AND not call mockedMapper.map
        AND return a Result.Failure with the Exception
 */
    @Test
    fun validateErrorResponse() = runTest {
        coEvery { mockedRepository.getItem(any()) } returns NetworkResponse.Error.ClientError(
            code = null,
            message = "message"
        )

        val result = useCase.execute("1")

        coVerify(exactly = 1) {
            mockedRepository.getItem("1")
        }

        coVerify(exactly = 0) {
            mockedMapper.map(any())
        }

        assertTrue(result.exceptionOrNull() is Exception)
    }

}