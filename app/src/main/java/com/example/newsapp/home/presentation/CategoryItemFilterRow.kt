package com.example.newsapp.home.presentation

import androidx.compose.runtime.Composable

@Composable
fun CategoryItemFilterRow(
//    selected: CategoryItem?,
//    onSelect: (CategoryItem?) -> Unit,
//    values: Array<CategoryItem> = getItems.toTypedArray(),
    showAllFilter: Boolean = true
)
{
//    val allSelected = selected == null
//
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        if (showAllFilter)
//            item {
//                FilterChip(
//                    selected = allSelected,
//                    onClick = { onSelect(null) },
//                    label = { Text("All") },
//                    colors = FilterChipDefaults.filterChipColors(
//                        selectedContainerColor = MaterialTheme.colorScheme.primary,
//                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
//                    ),
//                )
//            }
//
//        items(values) { category ->
//            FilterChip(
//                selected = selected == category,
//                onClick = { onSelect(category) },
//                label = { Text(category.label) },
//                colors = FilterChipDefaults.filterChipColors(
//                    selectedContainerColor = MaterialTheme.colorScheme.primary,
//                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
//        }
//    }
}