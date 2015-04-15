package com.example.taieb.immkd.parsing;


import java.util.LinkedList;
import java.util.List;

public class Article {
    private String Part_Num;
    private String Part_Name;
    private String CASE;
    private List<String> liste_BOX;
    private List<Station> liste_Station;


    public Article() {
        liste_BOX=new LinkedList<String>();
        liste_Station=new LinkedList<Station>();
    }

    public String getPart_Num() {
        return Part_Num;
    }

    public void setPart_Num(String part_Num) {
        Part_Num = part_Num;
    }

    public String getPart_Name() {
        return Part_Name;
    }

    public void setPart_Name(String part_Name) {
        Part_Name = part_Name;
    }

    public String getCASE() {
        return CASE;
    }

    public void setCASE(String CASE) {
        this.CASE = CASE;
    }

    public List<String> getListe_BOX() {
        return liste_BOX;
    }

    public void setListe_BOX(List<String> liste_BOX) {
        this.liste_BOX = liste_BOX;
    }

    public List<Station> getListe_Station() {
        return liste_Station;
    }

    public void setListe_Station(List<Station> liste_Station) {
        this.liste_Station = liste_Station;
    }

    public void CleanStationList()
    {
        if(liste_Station.size()>1)
        {
            Station s=liste_Station.get(0);
            boolean differant=false;

            for(int i=0;i<liste_Station.size();i++)
            {
                if(!liste_Station.get(i).equals(s))
                    differant=true;
                break;

            }

            if(!differant)
            {
                liste_Station.clear();
                liste_Station.add(s);
            }
        }
    }

    public void CleanBOXList()
    {
        if(liste_BOX.size()>1)
        {
            String s=liste_BOX.get(0);
            boolean differant=false;

            for(int i=0;i<liste_BOX.size();i++)
            {
                if(!liste_BOX.get(i).equals(s))
                    differant=true;
                break;

            }

            if(!differant)
            {
                String lastS=liste_BOX.get(liste_BOX.size()-1);
                liste_BOX.clear();
                if(s.equals(lastS)) {
                    liste_BOX.add(s);
                }else
                {
                    liste_BOX.add(s+"-"+lastS);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Article{" +
                "Part_Num='" + Part_Num + '\'' +
                ", Part_Name='" + Part_Name + '\'' +
                ", CASE='" + CASE + '\'' +
                ", liste_BOX=" + liste_BOX.get(0) +
                ", liste_Station=" + liste_Station.get(0).getStat_Name() +
                '}';
    }
}
