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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookbazaar.app.ui.components.BrandLogo
import com.bookbazaar.app.ui.theme.CardBorder
import com.bookbazaar.app.ui.theme.CreamBackground
import com.bookbazaar.app.ui.theme.CreamSurface
import com.bookbazaar.app.ui.theme.ForestPrimary
import com.bookbazaar.app.ui.theme.ForestTitle
import com.bookbazaar.app.ui.theme.TextDark
import com.bookbazaar.app.ui.theme.TextMuted
import com.bookbazaar.app.viewmodel.BookBazaarViewModel

@Composable
fun SignInScreen(
    viewModel: BookBazaarViewModel,
    onSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("reader@bookbazaar.in") }
    var password by remember { mutableStateOf("password") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        BrandLogo()
        Spacer(modifier = Modifier.height(24.dp))

        // Aside message box matching web auth-aside
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CreamSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Text(
                text = "WELCOME BACK",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = ForestPrimary,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "All the right books for a year of big ideas.",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Thoughtfully selected for every classroom, from Class 1 to 12.",
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Form Fields
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CreamSurface,
            unfocusedContainerColor = CreamSurface,
            focusedBorderColor = ForestPrimary,
            unfocusedBorderColor = CardBorder,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email address") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = ForestPrimary, modifier = Modifier.size(18.dp))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = ForestPrimary, modifier = Modifier.size(18.dp))
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                viewModel.signIn("Demo Reader", email)
                onSuccess()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ForestPrimary),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign in",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account? ", fontSize = 13.sp, color = TextMuted)
            Text(
                text = "Sign up",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ForestPrimary,
                modifier = Modifier.clickable { onNavigateToSignUp() }
            )
        }
    }
}

@Composable
fun SignUpScreen(
    viewModel: BookBazaarViewModel,
    onSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        BrandLogo()
        Spacer(modifier = Modifier.height(24.dp))

        // Aside message box matching web auth-aside
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CreamSurface)
                .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Text(
                text = "JOIN BOOK BAZAAR",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = ForestPrimary,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "A fresh chapter starts right here.",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Join families making school shopping a little simpler.",
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CreamSurface,
            unfocusedContainerColor = CreamSurface,
            focusedBorderColor = ForestPrimary,
            unfocusedBorderColor = CardBorder,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full name") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = ForestPrimary, modifier = Modifier.size(18.dp))
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email address") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = ForestPrimary, modifier = Modifier.size(18.dp))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = ForestPrimary, modifier = Modifier.size(18.dp))
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                val name = fullName.ifBlank { "New Reader" }
                val em = email.ifBlank { "reader@bookbazaar.in" }
                viewModel.signIn(name, em)
                onSuccess()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ForestPrimary),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Create account",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account? ", fontSize = 13.sp, color = TextMuted)
            Text(
                text = "Sign in",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = ForestPrimary,
                modifier = Modifier.clickable { onNavigateToSignIn() }
            )
        }
    }
}
