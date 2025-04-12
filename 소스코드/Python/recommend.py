import sys
from surprise import KNNWithMeans
from surprise import Dataset
from surprise import Reader
import pandas as pd
import pymysql


def load_data_from_db():
    # DB에 연결
    db = pymysql.connect(host='localhost', port=3306, user='root', password='1423', db='ml_chap3')

    try:
        with db.cursor() as cursor:
            sql = "SELECT userId, movieId, ratingScore FROM  ratings"
            cursor.execute(sql)
            rows = cursor.fetchall()

            if not isinstance(rows, list):
                rows = list(rows)

            ratings = pd.DataFrame(rows, columns=['userId', 'movieId', 'ratingScore'])
            reader = Reader(rating_scale=(1, 5))
            data = Dataset.load_from_df(ratings, reader)

            return data

    finally:
        db.close()

def recommend_movies(user_id):
    # 데이터 로드
    data = load_data_from_db()

    # KNN 알고리즘 설정
    # 이웃의 최대개수=40,  메모리 기반 협업 필터링 알고리즘, 유저기반 pearson 상관계수 적용
    algo = KNNWithMeans(k=40, sim_options={'name': 'pearson', 'user_based': True})

    # 알고리즘 학습
    trainset = data.build_full_trainset()
    algo.fit(trainset)

    # 사용자가 평가하지 않은 영화를 찾아서 데이터셋 형성.
    testset = trainset.build_anti_testset(fill=False)
    user_testset = [test for test in testset if test[0] == user_id]	# List Comprehension

    # 예측 수행
    predictions = algo.test(user_testset)

    # 예측 평점 순으로 정렬 (익명함수 람다를 이용하여 predictions 리스트의 원소의 est속성값을 기준으로 내림차순정렬) (x를 입력으로 하여 x.est를 리턴)
    predictions.sort(key=lambda x: x.est, reverse=True)

    # 상위 10개만 선택
    top10 = predictions[:10]

    # 결과를 DB에 저장
    result = [{'movieId': pred.iid, 'predictedRating': pred.est} for pred in top10]
    save_to_db(user_id, result)

    #------------------------------------------------------------------------------------------------------

    # 이웃의 최대개수=40, 메모리 기반 협업 필터링 알고리즘, 유저기반 cosine 유사도 적용
    algo = KNNWithMeans(k=40, sim_options={'name': 'cosine', 'user_based': True})

    # 알고리즘 학습
    trainset = data.build_full_trainset()
    algo.fit(trainset)

    # 사용자가 평가하지 않은 영화를 찾아서 데이터셋 형성.
    testset = trainset.build_anti_testset(fill=False)
    user_testset = [test for test in testset if test[0] == user_id]

    # 예측 수행
    predictions = algo.test(user_testset)

    # 예측 평점 순으로 정렬 (익명함수 람다를 이용하여 predictions 리스트의 원소의 est속성값을 기준으로 내림차순정렬) (x를 입력으로 하여 x.est를 리턴)
    predictions.sort(key=lambda x: x.est, reverse=True)

    # 상위 10개만 선택
    top10 = predictions[:10]

    # 결과를 DB에 저장
    result = [{'movieId': pred.iid, 'predictedRating': pred.est} for pred in top10]
    save_to_db_cosine(user_id, result)

def save_to_db(user_id, recommendations):
    # DB에 연결
    db = pymysql.connect(host='localhost', port=3306, user='root', password='1423', db='ml_chap3')

    try:
        with db.cursor() as cursor:
            # 기존 추천 목록 삭제
            sql = "DELETE FROM recommendations"
            cursor.execute(sql)


            # 새 추천 목록 추가
            sql = "INSERT INTO recommendations (recommendationId, userId, movieId, predictedRating) VALUES (%s, %s, %s, %s)"
            for i, recommendation in enumerate(recommendations):
                recommendation_id = i + 1
                cursor.execute(sql, (recommendation_id, user_id, recommendation['movieId'], recommendation['predictedRating']))

        # DB에 반영
        db.commit()
    finally:
        db.close()




def save_to_db_cosine(user_id, recommendations):
    # DB에 연결
    db = pymysql.connect(host='localhost', port=3306, user='root', password='1423', db='ml_chap3')

    try:
        with db.cursor() as cursor:
            # 기존 추천 목록 삭제
            sql = "DELETE FROM recommendations_cosine"
            cursor.execute(sql)


            # 새 추천 목록 추가
            sql = "INSERT INTO recommendations_cosine (recommendationId, userId, movieId, predictedRating) VALUES (%s, %s, %s, %s)"
            for i, recommendation in enumerate(recommendations):
                recommendation_id = i + 1
                cursor.execute(sql, (recommendation_id, user_id, recommendation['movieId'], recommendation['predictedRating']))

        # DB에 반영
        db.commit()
    finally:
        db.close()



if __name__ == '__main__':
    user_id = sys.argv[1]
    user_id = int(user_id)
    recommend_movies(user_id)

