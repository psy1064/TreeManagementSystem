package com.example.treemanagement;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button button;
    private String jsonString;
    ArrayList<Tree> treeArrayList;      // 나무정보들을 저장할 ArrayList
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.treeLocation);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JsonParse jsonParse = new JsonParse();      // AsyncTask 생성
                jsonParse.execute("http://121.153.150.157:81/select.php");     // AsyncTask 실행
            }
        });
    }

    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest";
        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용
            String url = strings[0];
            try {
                String selectData = "Data=" + editText.getText().toString();
                // 따옴표 안과 php의 post [ ] 안이 이름이 같아야 함
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(selectData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                // 어플에서 데이터 전송

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                } // 연결 상태 확인

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                Log.d(TAG, sb.toString().trim());

                return sb.toString().trim();        // 받아온 JSON 의 공백을 제거
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String fromdoInBackgroundString) { // doInBackgroundString에서 return한 값을 받음
            super.onPostExecute(fromdoInBackgroundString);

            if(fromdoInBackgroundString == null)
                textView.setText("error");
            else {
                jsonString = fromdoInBackgroundString;
                treeArrayList = doParse();
                if(treeArrayList.size() == 0)   textView.setText("검색결과 없음");
                // 객체의 크기가 0일때는 검색 결과가 없을 때이므로 검색결과 없음 설정
                else textView.setText(treeArrayList.get(0).getLocation());
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        private ArrayList<Tree> doParse() {
            ArrayList<Tree> tmpTreeArray = new ArrayList<Tree>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("Tree");

                for(int i=0;i<jsonArray.length();i++) {
                    Tree tmpTree = new Tree();
                    JSONObject item = jsonArray.getJSONObject(i);
                    tmpTree.setName(item.getString("이름"));
                    tmpTree.setCategory(item.getString("카테고리"));
                    tmpTree.setKeyword(item.getString("키워드"));
                    tmpTree.setNumber(item.getInt("보유수량"));
                    tmpTree.setPrice(item.getInt("주당가격"));
                    tmpTree.setLocation(item.getString("위치"));
                    tmpTree.setEct(item.getString("특이사항"));
                    tmpTree.setTreeHeight(item.getString("수고"));
                    tmpTree.setRootCollar(item.getString("근원직경"));
                    tmpTree.setBreastHeight(item.getString("흉고직경"));
                    tmpTree.setWidthCrown(item.getString("수관폭"));
                    tmpTree.setLength(item.getString("수관길이"));
                    tmpTree.setCrownHeight(item.getString("지하고"));
                    tmpTree.setManner(item.getString("육종방법"));
                    tmpTree.setDateTime(item.getString("입력시간"));

                    tmpTreeArray.add(tmpTree);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return tmpTreeArray;
        } // JSON을 가공하여 ArrayList에 넣음
    }
}