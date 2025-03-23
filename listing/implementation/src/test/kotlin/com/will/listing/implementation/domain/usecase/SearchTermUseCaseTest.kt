package com.will.listing.implementation.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.will.listing.implementation.domain.model.ProductCard
import com.will.listing.implementation.domain.model.TermHolder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

internal class SearchTermUseCaseTest {

    private val mockedPageSource = mockk<Pager<Int, ProductCard>>(relaxed = true)
    private val holder = TermHolder()
    private val useCase = SearchTermUseCaseImpl(
        listingPagingSource = mockedPageSource,
        termHolder = holder
    )

    /*
        WHEN call useCase.execute
        THEN change the holder term to the value passed in execute call
            AND call mockedPageSource.flow
            AND returns the flow object returned by paging source
     */
    @Test
    fun validateSuccessResponse() = runTest {
        val dummyFlow: Flow<PagingData<ProductCard>> = flowOf()
        every { mockedPageSource.flow } returns dummyFlow
        val result = useCase.execute("term")

        assertTrue(holder.term == "term")
        verify { mockedPageSource.flow }
        assertThat(result, equalTo(dummyFlow))
    }
}