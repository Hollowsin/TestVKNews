package com.example.tkid.testvknews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private Adapter adapter;

    VKList<VKApiPost> vkLists;

    String[] token = new String[]{"0b2632d40b2632d40b2632d4160b4030ad00b260b2632d450b403c3ecbb8311b68c115e"};

    private String[] scope = new String[]{VKScope.FRIENDS,VKScope.WALL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        VKSdk.initialize(this);
        VKSdk.login(this,scope);

        initRecyclerView();
    }
    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest news = new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.FILTERS, "post,photo",

                        VKApiConst.ACCESS_TOKEN, token ));

                news.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        vkLists=(VKList)response.parsedModel;
                        try {
                            JSONObject jsonObject = (JSONObject) response.json.get("response");
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            for( int i=0;i<jsonArray.length();i++) {
                                JSONObject post = (JSONObject) jsonArray.get(i);
                                if(post.has("attachments")) {
                                    JSONArray attachments = post.getJSONArray("attachments");
                                    JSONObject xz = attachments.getJSONObject(0);
                                    if (xz.has("photo")) {
                                        JSONObject photo = xz.getJSONObject("photo");
                                        List<Newsitem> z = Arrays.asList(new Newsitem((post.getString("text")), (photo.getString("photo_604"))));
                                        adapter.setItem(z);
                                    }
                                }
                                    else i++;
                                }
                            } catch (JSONException e1) {

                            e1.printStackTrace();
                        }
                    }
                });

            }
            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}


