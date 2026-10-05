package com.bookbazaar.app.ui.components

import androidx.compose.animation.Crossfade
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookbazaar.app.model.Book
import com.bookbazaar.app.ui.theme.BorderCard
import com.bookbazaar.app.ui.theme.CardSurfaceSubtle
import com.bookbazaar.app.ui.theme.CardWhite
import com.bookbazaar.app.ui.theme.DmSansFontFamily
import com.bookbazaar.app.ui.theme.ForestBrand
import com.bookbazaar.app.ui.theme.ForestTitle
import com.bookbazaar.app.ui.theme.GoldBadgeBg
import com.bookbazaar.app.ui.theme.GoldBadgeText
import com.bookbazaar.app.ui.theme.GoldStar
import com.bookbazaar.app.ui.theme.PlayfairDisplayFontFamily
import com.bookbazaar.app.ui.theme.StatusDeliveredText
import com.bookbazaar.app.ui.theme.TerracottaAccent
import com.bookbazaar.app.ui.theme.TextMuted

@Composable
fun BookCard(
    book: Book,
    isFavorite: Boolean,
    isAdding: Boolean,
    isAdded: Boolean,
    onAddToCart: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardWhite)
            .border(1.dp, BorderCard, RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Column {
            // Book cover art box with Badge and Favorite Heart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CardSurfaceSubtle),
                contentAlignment = Alignment.Center
            ) {
                // Render custom vector cover
                BookCover(
                    book = book,
                    modifier = Modifier
                        .width(125.dp)
                        .height(165.dp)
                )

                // Optional Badge (e.g. "New", "Bestseller", "Exam pick")
                if (!book.badge.isNullOrBlank()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(GoldBadgeBg)
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = book.badge,
                            fontFamily = DmSansFontFamily,
                            color = GoldBadgeText,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Heart Favorite Button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.92f))
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Save favorite",
                        tint = if (isFavorite) TerracottaAccent else TextMuted,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Meta tags: CLASS X · SUBJECT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CLASS ${book.classLevel}",
                    fontFamily = DmSansFontFamily,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestBrand,
                    letterSpacing = 0.6.sp
                )
                Text(
                    text = "·",
                    fontFamily = DmSansFontFamily,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
                Text(
                    text = book.subject,
                    fontFamily = DmSansFontFamily,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Book Title in Playfair Display
            Text(
                text = book.title,
                fontSize = 14.sp,
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                color = ForestTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            // Author in DM Sans
            Text(
                text = book.author,
                fontFamily = DmSansFontFamily,
                fontSize = 11.sp,
                color = TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Rating & Stock Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = GoldStar,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = String.format("%.1f", book.rating),
                    fontFamily = DmSansFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestTitle
                )
                Text(
                    text = "·",
                    fontFamily = DmSansFontFamily,
                    fontSize = 11.sp,
                    color = TextMuted
                )
                Text(
                    text = if (book.inStock) "In stock" else "Available soon",
                    fontFamily = DmSansFontFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (book.inStock) ForestBrand else TextMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Buy Row: Price + Add Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "₹${book.price}",
                        fontFamily = PlayfairDisplayFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestTitle
                    )
                    if (book.originalPrice > book.price) {
                        Text(
                            text = "₹${book.originalPrice}",
                            fontFamily = DmSansFontFamily,
                            fontSize = 11.sp,
                            color = TextMuted,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                // Add to cart button with plus, spinner, or checkmark feedback
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(if (isAdded) StatusDeliveredText else ForestBrand)
                        .clickable(enabled = book.inStock && !isAdding) { onAddToCart() },
                    contentAlignment = Alignment.Center
                ) {
                    Crossfade(targetState = Triple(isAdding, isAdded, book.inStock)) { state ->
                        when {
                            state.first -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(15.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            }
                            state.second -> {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Added",
                                    tint = Color.White,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                            else -> {
                                Text(
                                    text = "+",
                                    fontFamily = DmSansFontFamily,
                                    color = Color.White,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 19.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
