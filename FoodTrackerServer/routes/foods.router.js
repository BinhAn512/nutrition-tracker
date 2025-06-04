import express from 'express'
import foodsController from '../controllers/foods.controller.js'

const foodsRouter = express.Router()

foodsRouter.get("/foods", foodsController.getFoods)
foodsRouter.get("/food/:id", foodsController.getFoodById)

foodsRouter.post("/", foodsController.createFood);

export default foodsRouter