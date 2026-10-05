package com.bookbazaar.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookbazaar.app.ui.components.BrandLogo
import com.bookbazaar.app.ui.components.FeaturedOfferBanner
import com.bookbazaar.app.ui.theme.CardBorder
import com.bookbazaar.app.ui.theme.CreamBackground
import com.bookbazaar.app.ui.theme.CreamSurface
import com.bookbazaar.app.ui.theme.ForestPrimary
import com.bookbazaar.app.ui.theme.TextDark
import com.bookbazaar.app.ui.theme.TextMuted
import com.bookbazaar.app.viewmodel.BookBazaarViewModel

@Composable
fun LandingScreen(
    viewModel: BookBazaarViewModel,
    onNavigateToSignIn: () -> Unit,
    onNavigateToShop: () -> Unit
) {
    val promotions by viewModel.promotions.collectAsState()
    var activePromoIndex by remember { mutableIntStateOf(0) }
    val activePromo = promotions.getOrNull(activePromoIndex) ?: promotions.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        // Top Landing Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BrandLogo(compact = true)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(ForestPrimary)
                        .clickable { onNavigateToSignIn() }
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign in",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Browse Tabs: "By class" / "By subject"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(ForestPrimary)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "By class",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(CreamSurface)
                    .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
                    .clickable { onNavigateToShop() }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "By subject",
                    color = TextDark,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Featured Offer Card with Carousel dots
        if (activePromo != null) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                FeaturedOfferBanner(
                    promo = activePromo,
                    onExplore = onNavigateToShop
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Carousel Dots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    promotions.indices.forEach { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (index == activePromoIndex) 18.dp else 7.dp, 7.dp)
                                .clip(CircleShape)
                                .background(if (index == activePromoIndex) ForestPrimary else CardBorder)
                                .clickable { activePromoIndex = index }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Browse Subjects Section
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "THE RIGHT SUBJECT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Browse subjects",
                        fontSize = 19.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = ForestPrimary,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onNavigateToShop() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                viewModel.subjectFilters.forEach { subject ->
                    Column(
                        modifier = Modifier
                            .width(130.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(CreamSurface)
                            .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
                            .clickable {
                                viewModel.setShopSubjectFilter(subject.name)
                                onNavigateToShop()
                            }
                            .padding(12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CreamBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoStories,
                                contentDescription = null,
                                tint = ForestPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = subject.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "${subject.bookCount} titles",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Shop by Class Section
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "SCHOOL BOOKS, SORTED",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Shop by class",
                        fontSize = 19.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
                Text(
                    text = "${viewModel.catalogSummary.bookCount} books",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                viewModel.classFilters.forEach { grade ->
                    Column(
                        modifier = Modifier
                            .width(95.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(CreamSurface)
                            .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
                            .clickable {
                                viewModel.setShopClassFilter(grade.level)
                                onNavigateToShop()
                            }
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "CLASS",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = String.format("%02d", grade.level),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ForestPrimary
                        )
                        Text(
                            text = "${grade.bookCount} books",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Build your school list callout card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(ForestPrimary)
                .clickable { onNavigateToShop() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3B45F)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = ForestPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Build your school list",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Good books, all in one place",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
