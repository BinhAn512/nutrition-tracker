import express from 'express'
import favouriteFoodsController from '../controllers/favourite_foods.controller.js'

const favouriteFoodRouter = express.Router()

favouriteFoodRouter.get("/favourite_foods/:user_id", favouriteFoodsController.getFavouriteFoods)
favouriteFoodRouter.get("/favourite_food/", favouriteFoodsController.getFavouriteFood)

favouriteFoodRouter.post("/favourite_food/", favouriteFoodsController.createFavouriteFood);

favouriteFoodRouter.delete("/favourite_food", favouriteFoodsController.deleteFavouriteFood);

export default favouriteFoodRouter