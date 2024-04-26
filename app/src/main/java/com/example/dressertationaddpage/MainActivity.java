package com.example.dressertationaddpage;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {


    // text view variable to set the color for GFG text
    private TextView gfgTextView;

    // two buttons to open color picker dialog and one to
    // set the color for GFG text
    private Button mPickColorButton;

    // view box to preview the selected color
    private View vMajorColorPreview;
    private View vMinorColorPreview;
    private View vAddtlColorPreview;

    // this is the default color of the preview box
    private int mDefaultColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initiliaze options for spinner/dropdown for the Garment Type input
        Spinner spinnerGarmentType = (Spinner) findViewById(R.id.spinnerGarmentType);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapterGarmentType = ArrayAdapter.createFromResource(
                this,
                R.array.garmentTypes,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapterGarmentType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinnerGarmentType.setAdapter(adapterGarmentType);

        //repeat the above for the Where Stored spinner/dropdown
        Spinner spinnerWhereStored = (Spinner) findViewById(R.id.spinnerWhereStored);
        ArrayAdapter<CharSequence> adapterWhereStored = ArrayAdapter.createFromResource(
                this,
                R.array.whereStoredOptions,
                android.R.layout.simple_spinner_item
        );
        adapterWhereStored.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWhereStored.setAdapter(adapterWhereStored);


        /*
            Below is for adding the AmbilWarna color picker plugin for each color picker view.
            The tutorial I referenced is here:
            https://www.geeksforgeeks.org/how-to-create-a-basic-color-picker-tool-in-android/
            ... which is a simplified version of this color picker tutorial:
            https://www.geeksforgeeks.org/how-to-create-a-color-picker-tool-in-android-using-color-wheel-and-slider/
        */
        vMajorColorPreview = findViewById(R.id.majorColorView);
        vMinorColorPreview = findViewById(R.id.minorColorView);
        vAddtlColorPreview = findViewById(R.id.addtlColorView);

        // set the default color to 0 as it is black
        // for some reason, AmbilWarna uses ints for colors
        // unsure how to convert/translate to hex code
        mDefaultColor = 0;

        // for opening the AmbilWanra color picker dialog.
        vMajorColorPreview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openColorPickerDialogue(v);
                    }
                });

        vMinorColorPreview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openColorPickerDialogue(v);
                    }
                });

        vAddtlColorPreview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openColorPickerDialogue(v);
                    }
                });
    }

    // the dialog functionality is handled separately
    // using openColorPickerDialog this is triggered as
    // soon as the user clicks on the Pick Color button And
    // the AmbilWarnaDialog has 2 methods to be overridden
    // those are onCancel and onOk which handle the "Cancel"
    // and "OK" button of color picker dialog
    public void openColorPickerDialogue(View thisView) {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        mDefaultColor = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        thisView.setBackgroundColor(mDefaultColor);
                    }
                });
        colorPickerDialogue.show();
    }


    /*
    TODO
    the commented code below is from an attempt to access image files from the Pictures
    directory of the emulated Android (ideally we would be choosing from the Gallery,
    but since this is an emulator, I couldn't find a Gallery folder to navigate to).

    When pulling this repo at its current state, the Pictures directory will be empty,
    as I'm copying image files into it whenever I run Android studio; in order to test this,
    new test images will have to be copied (or otherwise downloaded) into there, or
    into another relevant folder.

    Anyway, the ultimate goal here was for the user to click an Upload button on the app,
    which allows them to open a navigator to their image files. To get the base functionality
    working, the first goal was to have the app access an image file saved to the phone
    immediately, without having to click a button. This first goal is where I ran into
    roadblocks, as I couldn't figure out the correct calls to make to access said files.
    Additionally, I'm admittedly not experienced enough to know what type I should store
    the image file objects as; ideally, once accessed, the image files are displayed in the
    app, and potentially parsed through pixel by pixel for color palette analytics.

    Additional comments are added throughout. Any help is appreciated!
    - Jacob U 4.26.2024


    P.S. for future reference, once the image is pulled, the below tools looked helpful
    for pulling colors/palettes from images:
    https://utkuglsvn.medium.com/what-is-palette-api-how-to-use-palette-api-1e594e371bb7
    https://developer.android.com/develop/ui/views/graphics/palette-colors
    https://github.com/skydoves/ColorPickerView
     */
    /*

    // for some reason, the path value below indicates DIRECTORY_PICTURES is empty, except
    // for an empty .thumbnails folder
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    //File file = new File(path, "shirt_black.jpg");
    File path2 = new File(path + "/.thumbnails");

    // for testing the elements contained in the above path
    String[] downloads = path.list();
    File[] downloadFiles = path.listFiles();
    String firstelement = downloadFiles[0].toString();
    File newpath = Environment.getExternalStoragePublicDirectory(firstelement);
    String[] newpathstrings = newpath.list();

    //Path path1 = Paths.get("");

    // from a stack overflow comment
    //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    //intent.addCategory(Intent.CATEGORY_OPENABLE);
    //intent.setType("application/pdf");

    //startActivityForResult(intent, 2);
    */

}