package ru.grandi.bambino.changeablerecycler

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val data = mutableListOf<ChangeableItem>()
    var numberItem = 0
    private val createItemScope =
        CoroutineScope(Dispatchers.Default + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            handlerError(throwable)
        })
    private val _createItemLiveData: MutableLiveData<MutableList<ChangeableItem>> = MutableLiveData()
    val createItemLiveData = _createItemLiveData

    fun asyncCreateItem() {
        createItemScope.coroutineContext.cancelChildren()
        createItemScope.launch {
            while (true){
                sleep(5000)
                val lastItem = repository.getAllChangeableItems().last()
                var number = lastItem.numberItem
                Log.e("data", "5 sek")
                val changeableItem = ChangeableItem(numberItem = ++number)
                addInDeletePull(changeableItem)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        createItemScope.coroutineContext.cancelChildren()
    }

    private fun handlerError(throwable: Throwable) {
        Log.e(TAG_COROUTINES_ERROR, throwable.message.toString())
    }

    private fun addInDeletePull(changeableItem: ChangeableItem){
        if (PULL_DELETE_ITEM.isEmpty()){
            addItemRandomPosition(changeableItem)
        }else {
            val positionDeleteItem = PULL_DELETE_ITEM.random()
            addItemRandomPosition(positionDeleteItem)
            PULL_DELETE_ITEM.remove(positionDeleteItem)
        }
    }

    private fun addItemRandomPosition(changeableItem: ChangeableItem) {
        data.addAll(repository.getAllChangeableItems())
        if (data.size == 0) {
            data.add(changeableItem)
        } else {
            data.add((Math.random() * data.size).toInt(), changeableItem)
        }
        _createItemLiveData.postValue(data)
    }

}