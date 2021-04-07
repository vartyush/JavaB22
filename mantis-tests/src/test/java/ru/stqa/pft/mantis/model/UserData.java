package ru.stqa.pft.mantis.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

    @Entity
    @Table(name = "mantis_user_table")
    public class UserData {
        @Id
        @Column(name = "id")
        private int id=Integer.MAX_VALUE;
        @Expose
        @Column(name = "username")
        private String username;
        @Expose
        @Column(name = "email")
        private String email;

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }


        public UserData withUsername (String username) {
            this.username = username;
            return this;
        }

        public UserData withEmail (String email) {
            this.username = email;
            return this;
        }

        public UserData withId (int id) {
            this.id = id;
            return this;
        }


        @Override
        public String toString() {
            return "UserData{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
