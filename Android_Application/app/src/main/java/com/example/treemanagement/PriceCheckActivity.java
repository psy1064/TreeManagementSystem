package com.example.treemanagement;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class PriceCheckActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우

        setContentView(R.layout.activity_price_check);

        ArrayList<Tree> findTreeList = (ArrayList<Tree>)getIntent().getSerializableExtra("TreeList");

        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        for(int i = 0 ; i < findTreeList.size() ; i++) {
            TableRow tableRow = new TableRow(this);     // tablerow 생성
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(24);
            textView.setText(findTreeList.get(i).getDateTime());
            tableRow.addView(textView);		// tableRow에 view 추가

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(24);

            textView.setText("수고 = " + findTreeList.get(i).getTreeHeight() +
                            "\t\t\t근원직경 = " + findTreeList.get(i).getRootCollar() +
                            "\t\t흉고직경 = " + findTreeList.get(i).getBreastHeight() +
                            "\n수관폭 = " + findTreeList.get(i).getWidthCrown() +
                            "\t\t수관길이 = " + findTreeList.get(i).getLength() +
                            "\t\t지하고 = " + findTreeList.get(i).getCrownHeight());
            tableRow.addView(textView);		// tableRow에 view 추가

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(24);

            textView.setText(String.valueOf(findTreeList.get(i).getCount()));
            tableRow.addView(textView);		// tableRow에 view 추가

            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(24);

            textView.setText(String.valueOf(findTreeList.get(i).getPrice()));
            tableRow.addView(textView);		// tableRow에 view 추가

            tableLayout.addView(tableRow);
        }
    }
}
