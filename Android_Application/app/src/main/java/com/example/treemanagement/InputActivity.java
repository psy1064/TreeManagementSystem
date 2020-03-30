package com.example.treemanagement;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {
    private TextView treeDetailText, finalPriceValue, countValueText;
    private EditText priceEditText, sellCountEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우

        setContentView(R.layout.activity_input);

        Tree tree = (Tree)getIntent().getSerializableExtra("Tree");     // MainActivity에서 넘긴 Class 받음
        
        treeDetailText = (TextView) findViewById(R.id.treeDetailText);
        finalPriceValue = (TextView) findViewById(R.id.finalPriceValue);
        countValueText = (TextView) findViewById(R.id.countValueText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        sellCountEditText = (EditText) findViewById(R.id.sellCountEditText);

        treeDetailText.setText("품명 = " + tree.getName() + "\n" +
                        "수고 = " + tree.getTreeHeight() +
                        "\t\t\t근원직경 = " + tree.getRootCollar() +
                        "\t\t흉고직경 = " + tree.getBreastHeight() +
                        "\n수관폭 = " + tree.getWidthCrown() +
                        "\t\t수관길이 = " + tree.getLength() +
                        "\t\t지하고 = " + tree.getCrownHeight());

        countValueText.setText(String.valueOf(tree.getCount()));


        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = Integer.parseInt(String.valueOf(sellCountEditText.getText()));
                int price = Integer.parseInt(String.valueOf(priceEditText.getText()));
                finalPriceValue.setText(String.valueOf(count * price));
            }
        });
    }
}
