package com.bookbazaar.app.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookbazaar.app.ui.components.PageHeading
import com.bookbazaar.app.ui.theme.AccentCoral
import com.bookbazaar.app.ui.theme.CardBorder
import com.bookbazaar.app.ui.theme.CreamBackground
import com.bookbazaar.app.ui.theme.CreamSurface
import com.bookbazaar.app.ui.theme.ForestPrimary
import com.bookbazaar.app.ui.theme.ForestTitle
import com.bookbazaar.app.ui.theme.TextDark
import com.bookbazaar.app.ui.theme.TextMuted
import com.bookbazaar.app.viewmodel.BookBazaarViewModel

@Composable
fun ProfileScreen(
    viewModel: BookBazaarViewModel,
    onNavigateToOrders: () -> Unit,
    onNavigateToCart: () -> Unit,
    onSignOut: () -> Unit
) {
    val user by viewModel.user.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val cart by viewModel.cart.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp)
    ) {
        PageHeading(
            kicker = "YOUR BOOK BAZAAR",
            title = "A little about you.",
            text = "Your account, your orders, your school-year essentials."
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Profile Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(CreamSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(18.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE5DECE)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (user.firstName.take(1).ifBlank { "D" }).uppercase(),
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = ForestTitle
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "SIGNED IN AS",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = ForestPrimary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = user.fullName,
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = user.email.ifBlank { "reader@bookbazaar.in" },
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(CreamBackground)
                    .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = ForestPrimary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Verified",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action rows
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(CreamSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(18.dp))
        ) {
            ProfileActionRow(
                icon = Icons.AutoMirrored.Filled.Assignment,
                title = "Your orders",
                subtitle = "${orders.size} orders placed",
                onClick = onNavigateToOrders
            )

            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardBorder))

            ProfileActionRow(
                icon = Icons.Default.ShoppingBag,
                title = "Your basket",
                subtitle = if (cart.itemCount > 0) "${cart.itemCount} items ready for checkout" else "Pick up where you left off",
                onClick = onNavigateToCart
            )

            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(CardBorder))

            ProfileActionRow(
                icon = Icons.AutoMirrored.Filled.Logout,
                title = "Sign out",
                subtitle = "See you again soon",
                isDestructive = true,
                onClick = {
                    viewModel.signOut()
                    onSignOut()
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Support / Help card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CreamSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                contentDescription = null,
                tint = ForestPrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Need a hand?",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "We’re happy to help with your order or book list.",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
            Text(
                text = "Get in touch →",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ForestPrimary,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:hello@bookbazaar.in")
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // ignore if no mail app
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileActionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (isDestructive) AccentCoral.copy(alpha = 0.1f) else CreamBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDestructive) AccentCoral else ForestPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDestructive) AccentCoral else TextDark
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextMuted
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}
