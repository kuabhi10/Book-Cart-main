package com.bookbazaar.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookbazaar.app.model.Order
import com.bookbazaar.app.ui.components.EmptyState
import com.bookbazaar.app.ui.components.PageHeading
import com.bookbazaar.app.ui.components.StatusPill
import com.bookbazaar.app.ui.theme.CardBorder
import com.bookbazaar.app.ui.theme.CreamBackground
import com.bookbazaar.app.ui.theme.CreamSurface
import com.bookbazaar.app.ui.theme.ForestPrimary
import com.bookbazaar.app.ui.theme.ForestTitle
import com.bookbazaar.app.ui.theme.GreenSuccess
import com.bookbazaar.app.ui.theme.GreenSuccessBg
import com.bookbazaar.app.ui.theme.TextDark
import com.bookbazaar.app.ui.theme.TextMuted
import com.bookbazaar.app.viewmodel.BookBazaarViewModel

@Composable
fun OrderDetailScreen(
    orderId: String,
    viewModel: BookBazaarViewModel,
    onBack: () -> Unit
) {
    val order: Order? = viewModel.getOrder(orderId)

    if (order == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBackground)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = ForestPrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Text("All orders", color = ForestPrimary, fontWeight = FontWeight.Bold)
            }
            EmptyState(
                title = "Order not found",
                text = "We couldn’t find details for this order.",
                actionLabel = "Back to orders",
                onActionClick = onBack
            )
        }
        return
    }

    val steps = listOf("placed", "processing", "shipped", "delivered")
    val currentStepIndex = steps.indexOf(order.status).coerceAtLeast(0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Back button
            Row(
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(top = 10.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = ForestPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "All orders",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ForestPrimary
                )
            }

            PageHeading(
                kicker = "ORDER #${order.id.takeLast(8).uppercase()}",
                title = "On its way to you.",
                text = "Placed on ${order.createdAt.take(10)}.",
                trailingContent = {
                    StatusPill(status = order.status)
                }
            )
        }

        // Delivery Progress Stepper Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CreamSurface)
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DELIVERY PROGRESS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestPrimary,
                        letterSpacing = 1.sp
                    )
                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = ForestPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    steps.forEachIndexed { index, stepName ->
                        val isDone = index <= currentStepIndex
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (isDone) ForestPrimary else CardBorder),
                                contentAlignment = Alignment.Center
                            ) {
                                if (index < currentStepIndex) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${index + 1}",
                                        color = if (isDone) Color.White else TextMuted,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = stepName.replaceFirstChar { it.uppercase() },
                                fontSize = 10.sp,
                                fontWeight = if (isDone) FontWeight.Bold else FontWeight.Normal,
                                color = if (isDone) ForestTitle else TextMuted
                            )
                        }
                    }
                }
            }
        }

        // In this parcel items section
        item {
            Text(
                text = "In this parcel",
                fontSize = 17.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        items(order.items, key = { it.bookId }) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CreamSurface)
                    .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(ForestPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.title.take(1),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "${item.quantity} × ₹${item.unitPrice}",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Text(
                    text = "₹${item.lineTotal}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestTitle
                )
            }
        }

        // Delivery details card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CreamSurface)
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "DELIVERING TO",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = order.customerName,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "${order.addressLine}\n${order.city}, ${order.state} ${order.postalCode}",
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardBorder))
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "PAYMENT",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cash on delivery\nPhone: ${order.phone}",
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardBorder))
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Order total",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "₹${order.total}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestTitle
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
