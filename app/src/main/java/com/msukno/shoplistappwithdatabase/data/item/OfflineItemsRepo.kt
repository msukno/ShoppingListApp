package com.msukno.shoplistappwithdatabase.data.item

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepo(private val itemDao: ItemDao) : ItemsRepo {

    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun updateItem(item: Item) = itemDao.update(item)

}