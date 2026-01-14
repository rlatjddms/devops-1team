import { createRouter, createWebHistory } from 'vue-router'
import InventoryPage from '../features/inventory/pages/InventoryPage.vue'
import LoginView from '../features/user/LoginView.vue'
import SignupView from '../features/user/SignupView.vue'
import ProfileView from '../features/user/ProfileView.vue'
import AdminUserView from '../features/user/AdminUserView.vue'
import ProductListView from '../features/order/ProductListView.vue'
import CartListView from '../features/order/CartListView.vue'
import OrderListView from '../features/order/OrderListView.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            // For now, redirect home to inventory or show a welcome page.
            // Let's keep it simple and redirect to inventory as it's the main feature we built.
            redirect: '/inventory'
        },
        {
            path: '/inventory',
            name: 'inventory',
            component: InventoryPage
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView
        },
        {
            path: '/signup',
            name: 'signup',
            component: SignupView
        },
        {
            path: '/profile',
            name: 'profile',
            component: ProfileView
        },
        {
            path: '/admin/search',
            name: 'admin-search',
            component: AdminUserView
        },
        {
            path: '/products',
            name: 'products',
            component: ProductListView
        },
        {
            path: '/cart',
            name: 'cart',
            component: CartListView
        },
        {
            path: '/orders',
            name: 'orders',
            component: OrderListView
        }
    ]
})

export default router
