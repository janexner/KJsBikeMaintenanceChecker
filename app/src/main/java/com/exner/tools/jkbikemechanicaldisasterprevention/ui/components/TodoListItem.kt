package com.exner.tools.jkbikemechanicaldisasterprevention.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.exner.tools.jkbikemechanicaldisasterprevention.database.entities.TemplateActivity
import com.exner.tools.jkbikemechanicaldisasterprevention.database.views.ActivityWithBikeData
import com.ramcosta.composedestinations.generated.destinations.ActivityDetailsDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun TodoListItem(
    activity: ActivityWithBikeData,
    destinationsNavigator: DestinationsNavigator,
    onCheckboxCallback: (Boolean) -> Unit,
    suppressBikeBadge: Boolean = false,
    suppressDueDate: Boolean = false
) {
    Surface(
        modifier = Modifier
            .clickable {
                destinationsNavigator.navigate(
                    ActivityDetailsDestination(
                        activity.activityUid
                    )
                )
            },
    ) {
        ListItem(
            overlineContent = {
                if (activity.bikeName != null && !suppressBikeBadge) {
                    Text(text = activity.bikeName, color = MaterialTheme.colorScheme.tertiary)
                }
            },
            headlineContent = {
                val headline = if (activity.activityDueDate != null && !suppressDueDate) {
                    "${activity.activityDueDate} - ${activity.activityTitle}"
                } else {
                    activity.activityTitle
                }
                Text(text = headline)
            },
            supportingContent = {
                Text(text = activity.activityDescription)
            },
            trailingContent = {
                Checkbox(
                    checked = activity.activityIsCompleted,
                    onCheckedChange = {
                        onCheckboxCallback(!activity.activityIsCompleted)
                    }
                )
            }
        )
    }
}

@Composable
fun TransientTodoListItem(
    activity: ActivityWithBikeData,
    onCheckboxCallback: (Boolean) -> Unit,
    suppressDueDate: Boolean = false
) {
    val overlineText = activity.activityRideLevel.name + if (activity.isEBikeSpecific) { " - eBike-specific" } else { "" }
    ListItem(
        overlineContent = {
            Text(text = overlineText, color = MaterialTheme.colorScheme.tertiary)
        },
        headlineContent = {
            val headline = if (activity.activityDueDate != null && !suppressDueDate) {
                "${activity.activityDueDate} - ${activity.activityTitle}"
            } else {
                activity.activityTitle
            }
            Text(text = headline)
        },
        supportingContent = {
            Text(text = activity.activityDescription)
        },
        trailingContent = {
            Checkbox(
                checked = activity.activityIsCompleted,
                onCheckedChange = {
                    onCheckboxCallback(!activity.activityIsCompleted)
                }
            )
        }
    )
}

@Composable
fun TemplateActivityListItem(
    templateActivity: TemplateActivity,
    onItemClick: (Long) -> Unit
) {
    val overlineContentText = templateActivity.rideLevel?.name + if (templateActivity.isEBikeSpecific) { " - eBike-specific" } else { "" }
    ListItem(
        modifier = Modifier.clickable { onItemClick(templateActivity.uid) },
        overlineContent = {
            Text(text = overlineContentText, color = MaterialTheme.colorScheme.tertiary)
        },
        headlineContent = {
            Text(text = templateActivity.title)
        },
        supportingContent = {
            Text(text = templateActivity.description)
        },
    )
}
