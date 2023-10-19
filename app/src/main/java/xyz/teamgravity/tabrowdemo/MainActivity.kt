package xyz.teamgravity.tabrowdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import xyz.teamgravity.tabrowdemo.ui.theme.TabRowDemoTheme

class MainActivity : ComponentActivity() {

    private val tabs: List<TabModel> = listOf(
        TabModel(
            label = R.string.home,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        TabModel(
            label = R.string.chat,
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email
        ),
        TabModel(
            label = R.string.settings,
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabRowDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = rememberPagerState { tabs.size }
                    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

                    LaunchedEffect(
                        key1 = selectedTabIndex,
                        block = {
                            state.animateScrollToPage(selectedTabIndex)
                        }
                    )

                    LaunchedEffect(
                        key1 = state.currentPage,
                        key2 = state.isScrollInProgress,
                        block = {
                            if (!state.isScrollInProgress) selectedTabIndex = state.currentPage
                        }
                    )

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TabRow(
                            selectedTabIndex = selectedTabIndex
                        ) {
                            tabs.forEachIndexed { index, tab ->
                                val selected = selectedTabIndex == index
                                Tab(
                                    selected = selected,
                                    text = {
                                        Text(text = stringResource(id = tab.label))
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = stringResource(id = tab.label)
                                        )
                                    },
                                    onClick = {
                                        selectedTabIndex = index
                                    }
                                )
                            }
                        }
                        HorizontalPager(
                            state = state,
                            key = { tabs[it].label },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                        ) { index ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(text = stringResource(id = tabs[index].label))
                            }
                        }
                    }
                }
            }
        }
    }

    data class TabModel(
        @StringRes val label: Int,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector
    )
}