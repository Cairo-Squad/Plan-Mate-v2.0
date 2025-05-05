package data.database.util

object MongoConstants {
	const val MONGO_DB_CONNECT_STRING = "mongodb+srv://ahmbdelkhalek:UufV0Zf4KpU9uFE7@planmate.ubgejn9.mongodb.net/?retryWrites=true&w=majority&appName=planMate"
	const val DATABASE_NAME = "plan_mate_management"
	
	const val ID = "_id"
	const val TITLE = "title"
	const val DESCRIPTION = "description"
	const val CREATED_BY = "createdBy"
	const val TASKS = "tasks"
	const val STATE_ID = "stateId"
	
	
	const val LOG_ENTITY_ID = "entityId"
	const val LOG_ENTITY_TITLE = "entityTitle"
	const val LOG_ENTITY_TYPE = "entityType"
	const val LOG_DATE_TIME = "dateTime"
	const val LOG_USER_ID = "userId"
	const val LOG_USER_ACTION = "userAction"
	
	
	const val STATE_TITLE = "title"
	
	const val TASK_ID = "_id"
	const val TASK_TITLE = "title"
	const val TASK_DESCRIPTION = "description"
	const val TASK_STATE_ID = "stateId"
	const val TASK_PROJECT_ID = "projectId"
	
	const val USER_ID = "_id"
	const val USER_NAME = "name"
	const val USER_PASSWORD = "password"
	const val USER_TYPE = "type"
}