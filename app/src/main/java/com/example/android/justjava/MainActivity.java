package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int quantity = 2;
    private String name;
    private boolean isCream, isChoco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
        Return quantity of cups of coffee
     */
    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        name = ((EditText) findViewById(R.id.name)).getText().toString();
        return name;
    }

    /*
        This method is called when the order button is clicked
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        displayMessage(orderSummaryTextView(price));
    }

    /*
        This method displays the given quantity value on the screen

        @param number is quantity

     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    /*
        This method calculated the price of purchase
     */
    private int calculatePrice() {
        isCream = ((CheckBox) findViewById(R.id.whipped_cream)).isChecked();
        isChoco = ((CheckBox) findViewById(R.id.chocolate)).isChecked();
        int basePrice = 5;
        if (isCream) basePrice++;
        if (isChoco) basePrice += 2;
        return quantity * basePrice;
    }

    /*
        This method displays the order summary of purchase
     */
    private String orderSummaryTextView(int totalPrice) {
        return String.format(Locale.ROOT, getString(R.string.order_form),
                getName(), isCream ? getString(R.string.yes) : getString(R.string.no), isChoco ? getString(R.string.yes_plus) : getString(R.string.no_plus), getQuantity(), totalPrice);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void showToast(View view, String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        }else{
            String message = getString(R.string.too_many_coffee);
            showToast(view, message);
        }
    }

    public void showMap(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6, -122.3"));
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }
    }

    public void composeEmail(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.tittle_message) + getName());
        intent.putExtra(Intent.EXTRA_TEXT, orderSummaryTextView(calculatePrice()));
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }
    }

    public void decrement(View view) {
        if (quantity >= 1) {
            quantity--;
            displayQuantity(quantity);
        } else {
            String message = getString(R.string.too_less_cups);
            showToast(view, message);
        }
    }
}
