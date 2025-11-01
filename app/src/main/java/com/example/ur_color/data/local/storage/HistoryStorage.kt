package com.example.ur_color.data.local.storage

//object HistoryStorage {
//    private const val HISTORY_FILE = "aura_state_history.json"
//    private val gson = Gson()
//
//    fun save(context: Context, user: UserData) {
//        val file = File(context.filesDir, HISTORY_FILE)
//        val list: MutableList<UserData> = if (file.exists()) {
//            gson.fromJson(file.readText(), object : TypeToken<MutableList<UserData>>() {}.type)
//        } else mutableListOf()
//        list.add(user)
//        if (list.size > 10) list.removeFirst()
//        file.writeText(gson.toJson(list))
//    }
//
//    fun load(context: Context): List<UserData> {
//        val file = File(context.filesDir, HISTORY_FILE)
//        return if (file.exists()) {
//            gson.fromJson(file.readText(), object : TypeToken<List<UserData>>() {}.type)
//        } else emptyList()
//    }
//
//    fun clear(context: Context) {
//        File(context.filesDir, HISTORY_FILE).takeIf { it.exists() }?.delete()
//    }
//}