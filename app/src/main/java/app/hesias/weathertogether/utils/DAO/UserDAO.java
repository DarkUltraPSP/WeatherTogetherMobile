package app.hesias.weathertogether.utils.DAO;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import app.hesias.weathertogether.Model.Report;
import app.hesias.weathertogether.utils.MySingleton;
import app.hesias.weathertogether.utils.VolleyResponseCallback;

public class UserDAO {

    private Context context;

    public UserDAO(Context context) {
        this.context = context;
    }

    String url = "http://192.168.1.44:8080/user";

    public void getAllUsers(VolleyResponseCallback callback) {
        List<Report> userList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
                Toast.makeText(context, "Success" + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
