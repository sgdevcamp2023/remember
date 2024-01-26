import {create} from 'zustand'

const CategoriesStore = create(set => ({
  categories: [],
  registerCategories: (categoryList) => set({categories: categoryList}),
  removeCategories: () => set({categories: []})
}))

export default CategoriesStore;