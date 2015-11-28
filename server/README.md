Setup instructions
==================

- Install Python3.3

- Create Python virtual environment
>virtualenv venv -p python3.3

- Activate virtual environment
> source venv/bin/activate

- Install dependencies
> pip install -r requirements.pip

Execution
==================
- To migrate DB (Everytime when the model is changed)
  > python manage.py migrate

- To run the server (this server is only for testing)
  > cd aiserverproj

  > python manage.py runserver
