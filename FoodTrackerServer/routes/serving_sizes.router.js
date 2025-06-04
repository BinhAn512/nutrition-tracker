import express from 'express'
import servingSizesController from '../controllers/serving_sizes.controller.js'

const servingSizesRouter = express.Router()

servingSizesRouter.get("/serving_sizes", servingSizesController.getServingSizes)
servingSizesRouter.get("/serving_size/:id", servingSizesController.getServingSizeById)

servingSizesRouter.post("/", servingSizesController.createServingSize);

export default servingSizesRouter