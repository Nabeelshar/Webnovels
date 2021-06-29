package my.noveldokusha.ui.databaseBookInfo

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import my.noveldokusha.BookMetadata
import my.noveldokusha.scraper.Response
import my.noveldokusha.scraper.fetchDoc
import my.noveldokusha.scraper.scrubber
import my.noveldokusha.ui.BaseViewModel

class DatabaseBookInfoModel : BaseViewModel()
{
	fun initialization(database: scrubber.database_interface, bookMetadata: BookMetadata) = callOneTime {
		this.database = database
		this.bookMetadata = bookMetadata
	}
	
	lateinit var database: scrubber.database_interface
	lateinit var bookMetadata: BookMetadata
	
	val bookDataLiveData by lazy {
		flow {
			val doc = fetchDoc(bookMetadata.url)
			emit(Response.Success(database.getBookData(doc)))
		}.flowOn(Dispatchers.IO).asLiveData()
	}
}