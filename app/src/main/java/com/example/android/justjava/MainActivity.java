package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if(quantity==100)
        {
            Toast.makeText((this), "Quantity cannot be greater than 100",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1)
        {
            Toast.makeText((this), "Quantity cannot be less than 1",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText namebox = findViewById(R.id.name_view);
        String name = namebox.getText().toString();
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean haswhippedCream= whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean haschocolate= chocolateCheckBox.isChecked();
        int price = calculatePrice(haswhippedCream , haschocolate);
        String pricemessage = createOrderSummary(price , haswhippedCream , haschocolate , name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: "+name);
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

    }
    }
    /**
     * Calculates the price of the order.
     *
     * quantity  is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addwhippedCream , boolean addChocolate) {
        int basePrice= 5;
        if (addwhippedCream){
            basePrice+= 1;
        }
        if (addChocolate){
            basePrice+= 2;
        }
        int price = quantity * basePrice;
        return price;
    }
    private String createOrderSummary(int price , boolean addwhippedCream , boolean addchocolate , String name) {
        String priceMessage= "Name: "+name;
        priceMessage += "\nAdd Whipped Cream? "+(addwhippedCream);
        priceMessage += "\nAdd Chocolate? "+(addchocolate);
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $"+(price) +"\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}