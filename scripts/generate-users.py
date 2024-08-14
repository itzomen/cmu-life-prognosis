import uuid
import random
from datetime import datetime, timedelta
import faker

# Constants
NUM_USERS = 500
PASSWORD_HASH = "9e8116dd15110c6da7f26e290c736c60577e61c99d327134ab53c7e71bec0c9f"
ROLES = ['admin', 'patient']
COUNTRIES = ['US', 'RW', 'ET', 'AL', 'CMU', 'JP']
FAKER = faker.Faker()

def generate_user_data():
    uuid_val = str(uuid.uuid4())
    email = FAKER.email()
    role = random.choice(ROLES)
    first_name = FAKER.first_name()
    last_name = FAKER.last_name()
    dob = FAKER.date_of_birth(minimum_age=18, maximum_age=90).strftime('%m/%d/%Y')
    country = random.choice(COUNTRIES)
    hiv_status = random.choice([True, False])
    diagnosis_date = (datetime.strptime(dob, '%m/%d/%Y') + timedelta(days=random.randint(365*18, 365*30))).strftime('%m/%d/%Y')
    art_status = hiv_status and random.choice([True, False])
    art_date = diagnosis_date if art_status else ''

    return f"{uuid_val}:{email}:{role}:{PASSWORD_HASH}:{first_name}:{last_name}:{dob}:{country}:{hiv_status}:{diagnosis_date}:{art_status}:{art_date}"

# Generate the users
users = [generate_user_data() for _ in range(NUM_USERS)]

# Save to a file or print out
with open('generated_users.txt', 'w') as file:
    for user in users:
        file.write(user + "\n")

print("500+ users generated successfully!")
