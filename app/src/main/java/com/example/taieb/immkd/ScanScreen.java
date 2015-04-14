package com.example.taieb.immkd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taieb.immkd.parsing.Article;
import com.example.taieb.immkd.parsing.parsingxls;
import com.example.taieb.immkd.util.SystemUiHider;
import com.javacodegeeks.androidqrcodeexample.R;

import java.io.InputStream;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ScanScreen extends Activity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";



    private int idmodele;
    private static final int QR_SEARCH = 1;
    private static final int PART_NUM_SEARCH = 2;
    private int SearchMode=-1;
    parsingxls fileparser;


    //QRCODE search

    public void scanQR() {

                try {

            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(ScanScreen.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }

    }
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                    System.out.println(anfe.toString());
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });


        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                System.out.println("hhhh " + contents);
                Toast toast = makeText(this, "Content:" + contents + " Format:" + format, LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast toast = makeText(this, "Scan was Cancelled!", LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();

            }
        }
    }


// simple Search
public void simplesearch(View v) {

    final ProgressDialog dialog =ProgressDialog.show(this, "Searching", "Please wait...", true);


    EditText mEdit = (EditText) findViewById(R.id.editText);
    //System.out.println(mEdit.getText().toString());
    String ValueToSearch= mEdit.getText().toString().trim();
    int searchresult=fileparser.findRow(this.idmodele,ValueToSearch);

    new Thread()
    {
        public void run()
        {
            try
            {
                sleep(2000);
            }
            catch (Exception e)
            {
//                Log.d("PRUEBA", e.getMessage());
            }
            dialog.dismiss();
        }
    }.start();



    if(searchresult<1) {
        System.out.println("NOTfound");
    }else {
        System.out.println(searchresult);
        Article art= fileparser.getallrows(idmodele, searchresult, ValueToSearch);
        LinearLayout layprincipal = (LinearLayout) findViewById(R.id.LLB1);
//        LinearLayout l1=(LinearLayout) getResources().getLayout(R.layout.layoutsearchok);
//        layprincipal.addView(l1);

        TextView txtcase = (TextView) findViewById(R.id.CASETEXT);
        txtcase.setText(art.getCASE());

        TextView txtbox = (TextView) findViewById(R.id.BOXTEXT);
        txtbox.setText(art.getListe_BOX().get(0));

        TextView txtPartName = (TextView) findViewById(R.id.PARTNAMETEXT);
        txtPartName.setText(art.getPart_Name());

       // EditText mEdit = (EditText) l1.get

    }
    fileparser.saveExcelFile(this,"");
}








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        this.idmodele = intent.getIntExtra("id_model", -1);
        this.SearchMode=intent.getIntExtra("searchmode", -1);

        fileparser = new parsingxls();
        InputStream myInput=getResources().openRawResource(R.raw.a);
        fileparser.readExcelFile(this, myInput);


        if (SearchMode == QR_SEARCH) {
            setContentView(R.layout.activity_scan_screen);
            scanQR();
        }
        else if (SearchMode == PART_NUM_SEARCH) {

            setContentView(R.layout.menusearch);

           //simplesearch();
        }
    }




/*
    public void foraymen(View v)
    {
        parsingxls fileA;
        parsingxls fileB;

        HSSFFont font ;//= myWorkBook.createFont();

        boolean trouve=false;
        final DataFormatter df = new DataFormatter();

        fileA = new parsingxls();
        InputStream myInput=getResources().openRawResource(R.raw.original);

        fileA.readExcelFile(this, myInput);
        HSSFSheet s1 = fileA.getMyWorkBook().getSheet("a");
        HSSFCellStyle cellStyleRED = fileA.getMyWorkBook().createCellStyle();
        HSSFCellStyle cellStyleGREEN = fileA.getMyWorkBook().createCellStyle();

        cellStyleRED.setFillBackgroundColor(HSSFColor.RED.index);
        cellStyleRED.setFillPattern(CellStyle.SOLID_FOREGROUND);

        cellStyleGREEN.setFillBackgroundColor(HSSFColor.GREEN.index);
        cellStyleGREEN.setFillPattern(CellStyle.SOLID_FOREGROUND);

        fileB = new parsingxls();
        InputStream myInput2=getResources().openRawResource(R.raw.finalll);
        fileB.readExcelFile(this, myInput2);
        HSSFSheet s2 = fileB.getMyWorkBook().getSheet("a");
        HSSFCellStyle cellStyleGREEN2 = fileB.getMyWorkBook().createCellStyle();
        cellStyleGREEN2.setFillBackgroundColor(HSSFColor.GREEN.index);
        cellStyleGREEN2.setFillPattern(CellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < s1.getPhysicalNumberOfRows(); i++) {
            Row row = s1.getRow(i);

            if (row!=null){
                Cell c1=row.getCell(5);
                String valueTocompare = df.formatCellValue(c1);
                System.out.println("valueTocompare    "+valueTocompare);
                trouve=false;
                for(int j=0;j<s2.getPhysicalNumberOfRows();j++)
                {
                    Row row2 = s2.getRow(j);
                    if (row!=null){
                        Cell c2=row2.getCell(5);
                        String value2 = df.formatCellValue(c2);
                        if(value2.trim().equals(valueTocompare.trim()))
                        {
                            trouve=true;
                            c1.setCellStyle(cellStyleGREEN);
                            c2.setCellStyle(cellStyleGREEN2);
                            for (int k=0;k<11;k++)
                            {
                                String val1 =df.formatCellValue(row.getCell(k));
                                String val2 =df.formatCellValue(row2.getCell(k));
                                if(val1.equals(val2))
                                {
                                    row.getCell(k).setCellStyle(cellStyleGREEN);
                                    row2.getCell(k).setCellStyle(cellStyleGREEN2);
                                }
                            }

                        }

                }

            }


    }





}


        fileA.saveExcelFile(this,"original.xls");
        fileB.saveExcelFile(this,"original2222222222222.xls");

    }


*/
}
