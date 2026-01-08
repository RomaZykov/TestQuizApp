package com.example.dailyquiztest

//@HiltAndroidTest
//class RoomTest : DataBase() {
//
//    private val guidesAsDraft = listOf(
//        GuideEntity(
//            id = "1",
//            title = "Test 1",
//            content = mapOf(
//                Pair(0, "Test content 1")
//            ),
//            isDraft = true,
//            latestModified = 0
//        ),
//        GuideEntity(
//            id = "2",
//            title = "Test 2",
//            content = mapOf(
//                Pair(0, "Complex test content 2"),
//                Pair(1, "Complex test content 2"),
//                Pair(2, "Complex test content 2"),
//                Pair(3, "Complex test content 2"),
//                Pair(4, "Complex test content 2"),
//                Pair(5, "Complex test content 2"),
//                Pair(6, "Complex test content 2"),
//                Pair(7, "Complex test content 2"),
//                Pair(8, "Complex test content 2"),
//                Pair(9, "Complex test content 2"),
//            ),
//            isDraft = true,
//            latestModified = 0
//        ),
//        GuideEntity(
//            id = "3",
//            title = "Test 3",
//            content = mapOf(
//                Pair(0, "Test content 3")
//            ),
//            isDraft = true,
//            latestModified = 0
//        )
//    )
//    private val guides = listOf(
//        GuideEntity(
//            id = "4",
//            title = "Test 4",
//            content = mapOf(
//                Pair(0, "Test content 4")
//            ),
//            isDraft = false,
//            latestModified = 1
//        ),
//        GuideEntity(
//            id = "5",
//            title = "Test 5",
//            content = mapOf(
//                Pair(0, "Test content 5")
//            ),
//            isDraft = false,
//            latestModified = 2
//        ),
//    )
//
//    @Test
//    @Throws(Exception::class)
//    fun saveGuidesAsDraft() = runBlocking {
//        guidesAsDraft.forEach {
//            guideDao.upsert(it)
//        }
//
//        assertEquals(3, guideDao.allGuides().first().size)
//        assertEquals(
//            listOf(
//                GuideEntity(
//                    id = "1",
//                    title = "Test 1",
//                    content = mapOf(
//                        Pair(0, "Test content 1")
//                    ),
//                    isDraft = true,
//                    latestModified = 0
//                ),
//                GuideEntity(
//                    id = "2",
//                    title = "Test 2",
//                    content = mapOf(
//                        Pair(0, "Complex test content 2"),
//                        Pair(1, "Complex test content 2"),
//                        Pair(2, "Complex test content 2"),
//                        Pair(3, "Complex test content 2"),
//                        Pair(4, "Complex test content 2"),
//                        Pair(5, "Complex test content 2"),
//                        Pair(6, "Complex test content 2"),
//                        Pair(7, "Complex test content 2"),
//                        Pair(8, "Complex test content 2"),
//                        Pair(9, "Complex test content 2")
//                    ),
//                    isDraft = true,
//                    latestModified = 0
//                )
//            ), guideDao.allGuides().first().take(2)
//        )
//    }
//}