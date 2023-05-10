        package com.example.intec.Entititer;

        import java.util.ArrayList;
        import java.util.Objects;

        public class Firma {
            private int ID;
            private String firmanavn;

            ArrayList<Person> ansatte = new ArrayList<>();

            public Firma() {
            }

            public Firma(String firmanavn) {
                this.firmanavn = firmanavn;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getFirmanavn() {
                return firmanavn;
            }

            public void setFirmanavn(String firmanavn) {
                this.firmanavn = firmanavn;
            }

            public ArrayList<Person> getAnsatte() {
                return ansatte;
            }

            public void setAnsatte(ArrayList<Person> ansatte) {
                this.ansatte = ansatte;
            }

            public String toString() {
                return "Firma{" +
                        "firmanavn='" + firmanavn + '\'' +
                        '}';
            }
        }
