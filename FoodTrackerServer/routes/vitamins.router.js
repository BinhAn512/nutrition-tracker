import express from 'express'
import vitaminsController from '../controllers/vitamins.controller.js'

const vitaminsRouter = express.Router()

vitaminsRouter.get("/vitamins", vitaminsController.getVitamins)
vitaminsRouter.get("/vitamin/:id", vitaminsController.getVitaminById)

vitaminsRouter.post("/", vitaminsController.createVitamin);

export default vitaminsRouter