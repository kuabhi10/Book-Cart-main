package com.bookbazaar.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookbazaar.app.ui.theme.BorderCard
import com.bookbazaar.app.ui.theme.CardWhite
import com.bookbazaar.app.ui.theme.DmSansFontFamily
import com.bookbazaar.app.ui.theme.ForestBrand
import com.bookbazaar.app.ui.theme.ForestTitle
import com.bookbazaar.app.ui.theme.PaperBackground
import com.bookbazaar.app.ui.theme.PlayfairDisplayFontFamily
import com.bookbazaar.app.ui.theme.TerracottaAccent
import com.bookbazaar.app.ui.theme.TerracottaBadge
import com.bookbazaar.app.ui.theme.TextBody
import com.bookbazaar.app.ui.theme.TextLight
import com.bookbazaar.app.ui.theme.TextMuted

@Composable
fun BrandLogo(
    compact: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable { onClick() } else Modifier
        )
    ) {
        // Asymmetric leaf border radius: 13px 13px 13px 4px from web stylesheet
        Box(
            modifier = Modifier
                .size(if (compact) 35.dp else 39.dp)
                .clip(RoundedCornerShape(topStart = 13.dp, topEnd = 13.dp, bottomEnd = 13.dp, bottomStart = 4.dp))
                .background(ForestBrand),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoStories,
                contentDescription = "Book Bazaar Logo",
                tint = Color(0xFFF8F1D9),
                modifier = Modifier.size(if (compact) 18.dp else 21.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color(0xFF264C3B),
                            fontFamily = DmSansFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("book")
                    }
                    withStyle(
                        SpanStyle(
                            color = TerracottaAccent,
                            fontFamily = DmSansFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("bazaar")
                    }
                },
                fontSize = if (compact) 19.sp else 21.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.8).sp,
                lineHeight = 20.sp
            )
            Text(
                text = "THE SCHOOL BOOKSHOP",
                fontFamily = DmSansFontFamily,
                fontSize = 7.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF78867B),
                letterSpacing = 1.1.sp,
                lineHeight = 9.sp
            )
        }
    }
}

@Composable
fun AppHeader(
    cartCount: Int,
    userInitial: String,
    isSignedIn: Boolean,
    onLogoClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onDeliveryClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFBF9F2))
            .border(width = 1.dp, color = Color(0xFFEAE5D9))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BrandLogo(
            compact = true,
            onClick = onLogoClick
        )

        // Location / Delivery Pill matching .header-location
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(PaperBackground)
                .border(1.dp, Color(0xFFE8E2D5), RoundedCornerShape(12.dp))
                .clickable { onDeliveryClick?.invoke() }
                .padding(horizontal = 9.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Location",
                tint = TerracottaAccent,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = "DELIVERY DETAILS",
                    fontFamily = DmSansFontFamily,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextLight,
                    letterSpacing = 0.7.sp
                )
                Text(
                    text = "Confirm at checkout",
                    fontFamily = DmSansFontFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF314F3F)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = TextLight,
                modifier = Modifier.size(14.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Cart button matching .header-cart (40px with border and terracotta badge)
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Transparent)
                    .border(1.dp, Color(0xFFE8E2D5), RoundedCornerShape(12.dp))
                    .clickable { onCartClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = "Shopping Bag",
                    tint = Color(0xFF345844),
                    modifier = Modifier.size(20.dp)
                )

                if (cartCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 2.dp, end = 2.dp)
                            .size(17.dp)
                            .clip(CircleShape)
                            .background(TerracottaBadge)
                            .border(1.5.dp, Color(0xFFFBF9F2), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (cartCount > 99) "99+" else cartCount.toString(),
                            fontFamily = DmSansFontFamily,
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Profile Avatar matching .header-avatar
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE5EADF))
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isSignedIn) userInitial.uppercase() else "P",
                    fontFamily = PlayfairDisplayFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF365A45)
                )
            }
        }
    }
}
