package com.example.treemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    Context context = this;
    private Button selectButton, markButton1;
    private String jsonString;
    ArrayList<Tree> treeArrayList;      // 나무정보들을 저장할 ArrayList
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우

        setContentView(R.layout.activity_main);

        selectButton = (Button) findViewById(R.id.select);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(getApplicationContext());
                et.setSingleLine();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("나무 이름 검색");
                builder.setView(et);
                builder.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectString = et.getText().toString();
                        et.setText("");
                        final JsonParse jsonParse = new JsonParse();      // AsyncTask 생성
                        jsonParse.execute("http://121.153.150.157:81/select.php",selectString);     // AsyncTask 실행
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public class JsonParse extends AsyncTask<String, Void, String> {
        String TAG = "JsonParseTest";
        @Override
        protected String doInBackground(String... strings) {
            // execute의 매개변수를 받아와서 사용
            String url = strings[0];
            String selectString = strings[1];
            try {
                String selectData = "Data=" + selectString;
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
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("검색결과");
            if(fromdoInBackgroundString == null) {
                builder.setMessage("검색할 수 없습니다.");
            }
            else {
                jsonString = fromdoInBackgroundString;
                treeArrayList = doParse();
                if(treeArrayList.size() == 0)   {
                    builder.setMessage("검색 결과가 없습니다.");
                }
                // 객체의 크기가 0일때는 검색 결과가 없을 때이므로 검색결과 없음 설정
                else {
                    CharSequence item[] = new CharSequence[treeArrayList.size()];
                    for(int i = 0 ; i < treeArrayList.size() ; i++) {
                        item[i] = "수고 = " + treeArrayList.get(i).getTreeHeight() +
                                  "\t근원직경 = " + treeArrayList.get(i).getRootCollar() +
                                  "\t흉고직경 = " + treeArrayList.get(i).getBreastHeight() +
                                  "\n수관폭 = " + treeArrayList.get(i).getWidthCrown() +
                                  "\t수관길이 = " + treeArrayList.get(i).getLength() +
                                  "\t지하고 = " + treeArrayList.get(i).getCrownHeight();
                    }
                    builder.setItems(item, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Tree intentTreeList = treeArrayList.get(which);
                            Intent intent = new Intent(MainActivity.this, InputActivity.class);
                            intent.putExtra("Tree", intentTreeList);
                            intent.putExtra("TreeList", treeArrayList);
                            startActivity(intent);
                        }
                    });

                }
            }
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
                    tmpTree.setCount(item.getInt("보유수량"));
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