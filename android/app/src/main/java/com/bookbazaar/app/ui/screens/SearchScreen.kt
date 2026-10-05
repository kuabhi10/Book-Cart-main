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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookbazaar.app.ui.components.BookCard
import com.bookbazaar.app.ui.components.EmptyState
import com.bookbazaar.app.ui.components.PageHeading
import com.bookbazaar.app.ui.theme.BorderCard
import com.bookbazaar.app.ui.theme.BorderInput
import com.bookbazaar.app.ui.theme.CardWhite
import com.bookbazaar.app.ui.theme.DmSansFontFamily
import com.bookbazaar.app.ui.theme.ForestBrand
import com.bookbazaar.app.ui.theme.ForestTitle
import com.bookbazaar.app.ui.theme.PaperBackground
import com.bookbazaar.app.ui.theme.PlayfairDisplayFontFamily
import com.bookbazaar.app.ui.theme.TerracottaAccent
import com.bookbazaar.app.ui.theme.TextMuted
import com.bookbazaar.app.viewmodel.BookBazaarViewModel

@Composable
fun SearchScreen(
    viewModel: BookBazaarViewModel
) {
    val query by viewModel.searchQuery.collectAsState()
    val searchClass by viewModel.searchClass.collectAsState()
    val searchSubject by viewModel.searchSubject.collectAsState()
    val books by viewModel.searchBooks.collectAsState()
    val savedIds by viewModel.savedBookIds.collectAsState()
    val addingBookId by viewModel.addingBookId.collectAsState()
    val addedBookId by viewModel.addedBookId.collectAsState()

    val focusManager = LocalFocusManager.current

    var classMenuExpanded by remember { mutableStateOf(false) }
    var subjectMenuExpanded by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(PaperBackground),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Page Heading
        item(span = { GridItemSpan(2) }) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                PageHeading(
                    kicker = "YOUR NEXT GREAT FIND",
                    title = "Find the right books.",
                    text = "Search by title, author or subject. Use filters to narrow the list.",
                    trailingContent = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(CardWhite)
                                .border(1.dp, BorderCard, RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${books.size} titles",
                                fontFamily = DmSansFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestBrand
                            )
                        }
                    }
                )

                // Search Bar input matching web .search-form
                OutlinedTextField(
                    value = query,
                    onValueChange = { viewModel.searchQuery.value = it },
                    placeholder = {
                        Text(
                            text = "Try ‘Mathematics’ or an author name",
                            fontFamily = DmSansFontFamily,
                            color = TextMuted,
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = ForestBrand,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (query.isNotBlank()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = TextMuted,
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable { viewModel.searchQuery.value = "" }
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CardWhite,
                        unfocusedContainerColor = CardWhite,
                        focusedBorderColor = ForestBrand,
                        unfocusedBorderColor = BorderInput,
                        focusedTextColor = ForestTitle,
                        unfocusedTextColor = ForestTitle
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Filter row: Class dropdown, Subject dropdown, Clear filters
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = ForestBrand,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "FILTER BY",
                            fontFamily = DmSansFontFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = ForestBrand,
                            letterSpacing = 0.8.sp
                        )
                    }

                    // Class Dropdown Filter
                    Box {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (searchClass != null) ForestBrand else CardWhite)
                                .border(1.dp, if (searchClass != null) ForestBrand else BorderCard, RoundedCornerShape(12.dp))
                                .clickable { classMenuExpanded = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (searchClass != null) "Class $searchClass" else "All classes",
                                fontFamily = DmSansFontFamily,
                                color = if (searchClass != null) Color.White else ForestTitle,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        DropdownMenu(
                            expanded = classMenuExpanded,
                            onDismissRequest = { classMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("All classes", fontFamily = DmSansFontFamily) },
                                onClick = {
                                    viewModel.searchClass.value = null
                                    classMenuExpanded = false
                                }
                            )
                            (1..12).forEach { cls ->
                                DropdownMenuItem(
                                    text = { Text("Class $cls", fontFamily = DmSansFontFamily) },
                                    onClick = {
                                        viewModel.searchClass.value = cls
                                        classMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Subject Dropdown Filter
                    Box {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (searchSubject.isNotBlank()) ForestBrand else CardWhite)
                                .border(1.dp, if (searchSubject.isNotBlank()) ForestBrand else BorderCard, RoundedCornerShape(12.dp))
                                .clickable { subjectMenuExpanded = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (searchSubject.isNotBlank()) searchSubject else "All subjects",
                                fontFamily = DmSansFontFamily,
                                color = if (searchSubject.isNotBlank()) Color.White else ForestTitle,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        DropdownMenu(
                            expanded = subjectMenuExpanded,
                            onDismissRequest = { subjectMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("All subjects", fontFamily = DmSansFontFamily) },
                                onClick = {
                                    viewModel.searchSubject.value = ""
                                    subjectMenuExpanded = false
                                }
                            )
                            viewModel.subjectFilters.forEach { s ->
                                DropdownMenuItem(
                                    text = { Text(s.name, fontFamily = DmSansFontFamily) },
                                    onClick = {
                                        viewModel.searchSubject.value = s.name
                                        subjectMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Clear Filters Button (.clear-filters terracotta)
                    if (query.isNotBlank() || searchClass != null || searchSubject.isNotBlank()) {
                        Text(
                            text = "Clear filters",
                            fontFamily = DmSansFontFamily,
                            color = TerracottaAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .clickable { viewModel.clearSearchFilters() }
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Search Results Grid
        if (books.isEmpty()) {
            item(span = { GridItemSpan(2) }) {
                EmptyState(
                    title = "Nothing on this shelf.",
                    text = "Try a shorter search or clear a filter to see more books.",
                    actionLabel = "See all books",
                    onActionClick = { viewModel.clearSearchFilters() }
                )
            }
        } else {
            items(books, key = { it.id }) { book ->
                Box(modifier = Modifier.padding(start = 16.dp, end = 4.dp)) {
                    BookCard(
                        book = book,
                        isFavorite = savedIds.contains(book.id),
                        isAdding = addingBookId == book.id,
                        isAdded = addedBookId == book.id,
                        onAddToCart = { viewModel.addToCart(book) },
                        onToggleFavorite = { viewModel.toggleFavorite(book.id) }
                    )
                }
            }
        }

        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
