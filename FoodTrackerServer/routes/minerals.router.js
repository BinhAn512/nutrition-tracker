import express from 'express'
import mineralsController from '../controllers/minerals.controller.js'

const mineralsRouter = express.Router()

mineralsRouter.get("/minerals", mineralsController.getMinerals)
mineralsRouter.get("/mineral/:id", mineralsController.getMineralById)

mineralsRouter.post("/", mineralsController.createMineral);

export default mineralsRouter