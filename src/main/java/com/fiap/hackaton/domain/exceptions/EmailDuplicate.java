package com.fiap.hackaton.domain.exceptions;

public class EmailDuplicate extends RuntimeException {

        public EmailDuplicate(String message) {
            super(message);
        }
}
