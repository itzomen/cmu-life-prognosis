# CMU Life Prognosis

## Quick Start

1. Install Java SDK and the appropriate IDE.
2. Run the `main.java` file in the `src` directory.
3. Run and login as an admin with the following credentials:
   - Email: `admin@email.com`
   - Password: `password`
4. Login as a user with the following credentials:
   - Email: `patient@email.com`
   - Password: `passworD123`

## File Store Description

The file `user-store.txt` is used to store user data in the following format:

`uuid:email:role:hashed_password:first_name:last_name:dob:country:hiv_status:diagnosis_date:art_status:art_date`

Each field is separated by a colon `:`.

The fields are as follows:

- `uuid`: A unique identifier for the user.
- `email`: The email address of the user.
- `role`: The role of the user. Can be either `admin` or `patient`.
- `hashed_password`: The hashed password of the user.
- `first_name`: The first name of the user.
- `last_name`: The last name of the user.
- `dob`: The date of birth of the user.
- `country`: The country of the user.
- `hiv_status`: The HIV status of the user. (true or false)
- `diagnosis_date`: The date of diagnosis of the user.
- `art_status`: The ART status of the user. (true or false)
- `art_date`: The date the patient started ART drugs.


## TODO

- Create assignment checklist.
- Add Iso code to user and update export to include it.
- Add descriptions of all the files in user-store.txt or files used as data store.
- No passing the password through the provider, do authentication immediately.
- Change date format for saving to the data store.
