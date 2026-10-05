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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.bookbazaar.app.ui.theme.TextDark
import com.bookbazaar.app.ui.theme.TextMuted
import com.bookbazaar.app.viewmodel.BookBazaarViewModel

@Composable
fun OrdersScreen(
    viewModel: BookBazaarViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToShop: () -> Unit
) {
    val orders by viewModel.orders.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            PageHeading(
                kicker = "THE JOURNEY SO FAR",
                title = "Your orders.",
                text = "All the books you’ve brought home, in one place."
            )
        }

        if (orders.isEmpty()) {
            item {
                EmptyState(
                    title = "Your story starts with a book.",
                    text = "Once you place an order, you’ll find updates and delivery details here.",
                    actionLabel = "Explore the books",
                    onActionClick = onNavigateToShop
                )
            }
        } else {
            items(orders, key = { it.id }) { order ->
                OrderCard(
                    order = order,
                    onClick = { onNavigateToDetail(order.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CreamSurface)
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        // Head: Status Pill, Date, Arrow
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusPill(status = order.status)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = order.createdAt.take(10),
                    fontSize = 12.sp,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.NorthEast,
                    contentDescription = "View Details",
                    tint = ForestPrimary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Items preview
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                val firstTitle = order.items.firstOrNull()?.title ?: "School book"
                val extraCount = (order.items.size - 1).coerceAtLeast(0)
                Text(
                    text = if (extraCount > 0) "$firstTitle + $extraCount more" else firstTitle,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${order.itemCount} books · Cash on delivery",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardBorder))
        Spacer(modifier = Modifier.height(10.dp))

        // Foot: Order ID and Total
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Order #${order.id.takeLast(8).uppercase()}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextMuted
            )
            Text(
                text = "₹${order.total}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ForestTitle
            )
        }
    }
}
