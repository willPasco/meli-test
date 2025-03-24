package com.will.core.style.theme

import androidx.compose.ui.graphics.Color

public sealed interface MeliTestDesignSystem {

    public data object Colors: MeliTestDesignSystem {

        public val mainColor: Color = Color(0xBAFFEB3B)
        public val black: Color = Color.Black
        public val gray: Color = Color.Gray
        public val softGray: Color = Color(0xB54B4B4B)
        public val white: Color = Color.White
        public val offWhite: Color = Color(0xB5F3F3F3)
        public val green: Color = Color(0xB5015E12)
    }
}