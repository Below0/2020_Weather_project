import pymongo, os


class MongoDbManager:
    _instance = None
    client = pymongo.MongoClient(host=os.environ['IP'],
                                 port=27017)
    database = client['scsc']['score']

    @classmethod
    def __new__(cls, *args, **kwargs):
        if not cls._instance:
            cls._instance = object.__new__(cls)
        return cls._instance

    @classmethod
    def get_score_from_collection(cls, _query):
        assert cls.database
        return cls.database.find(_query)

    @classmethod
    def get_sorted_score(cls, _query, _key):
        assert cls.database
        return cls.database.find(_query)
