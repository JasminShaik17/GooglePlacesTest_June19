package cubex.mahesh.googleplacestest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var sp1:Spinner? = null
    var lview:ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp1 = findViewById(R.id.sp1)
        lview = findViewById(R.id.lview)

    }
    fun getPlaces(v:View){

        var r = Retrofit.Builder().
                        baseUrl("https://maps.googleapis.com/").
                addConverterFactory(GsonConverterFactory.create()).
                build()
        var api = r.create(PlacesAPI::class.java)
        var call:Call<PlacesPOJO> = api.getPlaces(sp1!!.selectedItem.toString())
        call.enqueue(object : retrofit2.Callback<PlacesPOJO> {
            override fun onFailure(call: Call<PlacesPOJO>?, t: Throwable?) {

           }

            override fun onResponse(call: Call<PlacesPOJO>?,
                                    response: Response<PlacesPOJO>?) {
             var pojo =    response?.body()
             var list:List<ResultsItem>? = pojo!!.results
             var temp_list =   mutableListOf<String>()
                for(x in list!!){
                        temp_list.add(x.name+"\n"+x.vicinity)
                }
                var myadapter = ArrayAdapter(this@MainActivity,
                       R.layout.indiview,
                        temp_list)
                lview?.adapter = myadapter
            }
        })
    }
}
