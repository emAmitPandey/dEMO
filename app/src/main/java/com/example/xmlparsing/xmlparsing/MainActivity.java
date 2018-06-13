package com.example.xmlparsing.xmlparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.xmlparsing.xmlparsing.PojoClass.Employee;

import org.xml.sax.Attributes;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void readXml(View view) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            parser.parse(getAssets().open("employees.xml"),
                    new DefaultHandler(){

                        Employee e;
                        ArrayList<Employee>list;
                        String msg = "";

                        @Override
                        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                            super.startElement(uri, localName, qName, attributes);
                            if (qName.equals("employees")){
                                list= new ArrayList<>();
                            }

                            if (qName.equals("employee")){
                                e= new Employee();
                            }
                        }

                        @Override
                        public void characters(char[] ch, int start, int length) throws SAXException {
                            super.characters(ch, start, length);
                            msg = new String(ch,start,length);

                        }

                        @Override
                        public void endElement(String uri, String localName, String qName) throws SAXException {
                            super.endElement(uri, localName, qName);

                            if (qName.equals("id")){
                                e.setId(Integer.parseInt(msg));
                            }

                            if (qName.equals("name")){
                                e.setName(msg);
                            }

                            if (qName.equals("design")){
                                e.setDesign(msg);
                            }

                            if (qName.equals("dept")){
                                e.setDept(msg);
                            }

                            if (qName.equals("employee")){
                                list.add(e);
                            }

                            if (qName.equals("employees")){
                                ArrayList<String> tempList = new ArrayList<>();

                                for (Employee e:list){
                                    tempList.add(e.getId()+
                                            "|"+e.getName()+
                                            "|"+e.getDesign()+
                                            "|"+e.getDept());
                                }

                                ArrayAdapter<String>adapter = new ArrayAdapter<String>
                                        (MainActivity.this,
                                        android.R.layout.simple_spinner_item,tempList);
                                ListView listView = findViewById(R.id.listView);
                                listView.setAdapter(adapter);
                            }

                        }
                    });
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
