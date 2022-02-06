# Paymybuddy

La base de donnée PostgreSQl se nomme: paymybuddy (configurée dans application.properties) de l'api et du web
 
Script Sql

CREATE TABLE public.Payment_type (
payment_type_id INTEGER NOT NULL,
payment_type VARCHAR NOT NULL,
CONSTRAINT payment_type_pk PRIMARY KEY (payment_type_id)
);

CREATE SEQUENCE public.users_users_id_seq;

CREATE TABLE public.Users (
users_id INTEGER NOT NULL DEFAULT nextval('public.users_users_id_seq'),
users_password VARCHAR NOT NULL,
users_lastname VARCHAR(30) NOT NULL,
users_firstname VARCHAR(30) NOT NULL,
users_address VARCHAR(50),
users_zip VARCHAR(5),
users_roles VARCHAR NOT NULL,
users_city VARCHAR(30),
users_phone VARCHAR NOT NULL,
users_email VARCHAR NOT NULL,
users_balance NUMERIC NOT NULL,
users_iban VARCHAR NOT NULL,
users_swift VARCHAR NOT NULL,
CONSTRAINT id_user_pk PRIMARY KEY (users_id)
);


ALTER SEQUENCE public.users_users_id_seq OWNED BY public.Users.users_id;

CREATE UNIQUE INDEX users_idx
ON public.Users
( users_email );

CREATE SEQUENCE public.transaction_transaction_id_seq;

CREATE TABLE public.Transaction (
transaction_id INTEGER NOT NULL DEFAULT nextval('public.transaction_transaction_id_seq'),
payment_type_id INTEGER NOT NULL,
users_id INTEGER NOT NULL,
transaction_user_receiver_id INTEGER NOT NULL,
transaction_date DATE NOT NULL,
transaction_amount NUMERIC NOT NULL,
transaction_commission_amount NUMERIC NOT NULL,
transaction_total_payment NUMERIC NOT NULL,
transaction_listcontact VARCHAR NOT NULL,
transaction_description VARCHAR NOT NULL,
CONSTRAINT transaction_pk PRIMARY KEY (transaction_id)
);


ALTER SEQUENCE public.transaction_transaction_id_seq OWNED BY public.Transaction.transaction_id;

CREATE SEQUENCE public.contact_contact_id_seq_1_1;

CREATE TABLE public.Contact (
contact_id INTEGER NOT NULL DEFAULT nextval('public.contact_contact_id_seq_1_1'),
users_id INTEGER NOT NULL,
users_received_id INTEGER NOT NULL,
CONSTRAINT contact_pk PRIMARY KEY (contact_id)
);


ALTER SEQUENCE public.contact_contact_id_seq_1_1 OWNED BY public.Contact.contact_id;

ALTER TABLE public.Transaction ADD CONSTRAINT payment_type_transaction_fk
FOREIGN KEY (payment_type_id)
REFERENCES public.Payment_type (payment_type_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Contact ADD CONSTRAINT users_contact_fk
FOREIGN KEY (users_id)
REFERENCES public.Users (users_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Transaction ADD CONSTRAINT users_transaction_fk
FOREIGN KEY (users_id)
REFERENCES public.Users (users_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

INSERT INTO payment_type (payment_type_id,payment_type) VALUES ('1', 'account'),('2','rib');
