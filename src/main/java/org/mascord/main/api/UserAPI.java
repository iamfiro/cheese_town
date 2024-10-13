package org.mascord.main.api;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import org.mascord.main.utils.MongoDB;

import java.util.UUID;

public class UserAPI {
    private final MongoDB mongoManager;

    public UserAPI() {
        this.mongoManager = MongoDB.getInstance();

        System.out.println("UserAPI 인스턴스 생성됨.");
    }

    // MongoDB에서 플레이어 데이터를 가져오는 메서드

    /*
    * GET 메서드 함수
    */

    // MongoDB에서 플레이어 데이터를 가져오는 메서드
    public Document getPlayerData(UUID playerUUID) {
        MongoCollection<Document> collection = mongoManager.getDatabase().getCollection("players");
        return collection.find(Filters.eq("uuid", playerUUID.toString())).first();
    }

    // 서버 처음 접속시 플레이어 데이터를 초기화하는 메서드
    public Document initializePlayerData(UUID playerUUID, String playerName) {
        MongoCollection<Document> collection = mongoManager.getDatabase().getCollection("players");
        Document playerData = new Document("uuid", playerUUID.toString())
                .append("playerName", playerName)
                .append("money", 0)
                .append("cheese", 0);

        collection.insertOne(playerData); // 데이터를 컬렉션에 저장
        System.out.println("새 유저 데이터 저장: " + playerName);
        return playerData;
    }

    // 유저가 존재하는지 확인하는 함수
    public boolean isPlayerExists(UUID playerUUID) {
        MongoCollection<Document> collection = mongoManager.getDatabase().getCollection("players");
        return collection.find(Filters.eq("uuid", playerUUID.toString())).first() != null;
    }
}
