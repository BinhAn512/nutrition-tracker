import express from 'express'
import foodLogsController from '../controllers/food_logs.controller.js'

const foodLogsRouter = express.Router()


foodLogsRouter.get("/food_logs", foodLogsController.getFoodLogs)
foodLogsRouter.get("/food_logs/food", foodLogsController.getFoodByDay)
foodLogsRouter.get("/food_logs/food", foodLogsController.getFoodByMeal)
foodLogsRouter.get("/food_logs/macro/:date", foodLogsController.getMacroByDay)
foodLogsRouter.get("/food_log/:id", foodLogsController.getFoodLogById)

foodLogsRouter.post("/food_log", foodLogsController.createFoodLog);

export default foodLogsRouter