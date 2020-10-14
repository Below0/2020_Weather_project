import pymongo
import os


class MongoDbManager:
    _instance = None
    client = pymongo.MongoClient(host=os.environ['IP'],
                                 port=27017)
    database = client['scsc']['weather']

    @classmethod
    def __new__(cls, *args, **kwargs):
        if not cls._instance:
            cls._instance = object.__new__(cls)
        return cls._instance

    @classmethod
    def get_weather_from_collection(cls, _query):
        assert cls.database
        return cls.database.find(_query)
