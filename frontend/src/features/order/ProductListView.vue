<script setup>
import { ref } from 'vue';
import { cartApi } from '../../api/cartApi';
import { useRouter } from 'vue-router';

const router = useRouter();

// 사용자 제공 테이블 기반 데이터 (10개)
const products = ref([
  {
    productId: 1,
    productCode: 'PROD-001',
    productName: '서울 신선 우유 1L',
    category: 'BEVERAGE',
    costPrice: 1800.00,
    price: 2600.00,
    description: '신선한 1등급 원유 100% 우유입니다.',
    expirationDate: '2026-01-25',
    leadTime: 2,
    safetyStock: 50,
    unit: 'EA',
    url: 'https://images.unsplash.com/photo-1563636619-e9108b9355ce?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 2,
    productCode: 'PROD-002',
    productName: '무항생제 달걀 30구',
    category: 'BEVERAGE',
    costPrice: 5000.00,
    price: 7500.00,
    description: '동물복지 인증을 받은 무항생제 달걀입니다.',
    expirationDate: '2026-02-10',
    leadTime: 3,
    safetyStock: 20,
    unit: 'BOX',
    url: 'https://images.unsplash.com/photo-1582722872445-41ef5aba16db?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 3,
    productCode: 'PROD-003',
    productName: '청송 꿀사과 5kg',
    category: 'BEVERAGE',
    costPrice: 15000.00,
    price: 22000.00,
    description: '경북 청송에서 재배한 고당도 꿀사과입니다.',
    expirationDate: '2026-03-01',
    leadTime: 5,
    safetyStock: 10,
    unit: 'BOX',
    url: 'https://images.unsplash.com/photo-1567306226416-28f0efdc88ce?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 4,
    productCode: 'PROD-004',
    productName: '한돈 삼겹살 100g',
    category: 'BEVERAGE',
    costPrice: 2200.00,
    price: 3500.00,
    description: '국내산 한돈, 구이용 삼겹살입니다.',
    expirationDate: '2026-01-20',
    leadTime: 2,
    safetyStock: 100,
    unit: 'G',
    url: 'https://images.unsplash.com/photo-1602470520998-f4a5ec4b92be?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 5,
    productCode: 'PROD-005',
    productName: '임금님표 이천쌀 20kg',
    category: 'BEVERAGE',
    costPrice: 38000.00,
    price: 52000.00,
    description: '2025년 가을에 수확한 햅쌀입니다.',
    expirationDate: '2027-01-01',
    leadTime: 7,
    safetyStock: 5,
    unit: 'BAG',
    url: 'https://images.unsplash.com/photo-1586201375761-83865001e31c?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 6,
    productCode: 'PROD-006',
    productName: '제주 삼다수 2L',
    category: 'BEVERAGE',
    costPrice: 400.00,
    price: 1000.00,
    description: '제주 화산 암반수로 만든 깨끗한 생수입니다.',
    expirationDate: '2027-06-30',
    leadTime: 3,
    safetyStock: 200,
    unit: 'EA',
    url: 'https://images.unsplash.com/photo-1548839140-29a749e1cf4d?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 7,
    productCode: 'PROD-007',
    productName: '국산 콩 두부 300g',
    category: 'BEVERAGE',
    costPrice: 1200.00,
    price: 2000.00,
    description: '국산콩 100%로 만든 고소한 찌개용 두부입니다.',
    expirationDate: '2026-01-28',
    leadTime: 2,
    safetyStock: 30,
    unit: 'EA',
    url: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 8,
    productCode: 'PROD-008',
    productName: '신라면 멀티팩(5입)',
    category: 'BEVERAGE',
    costPrice: 3500.00,
    price: 4800.00,
    description: '얼큰하고 진한 국물 맛이 일품인 라면입니다.',
    expirationDate: '2026-08-15',
    leadTime: 4,
    safetyStock: 50,
    unit: 'PACK',
    url: 'https://images.unsplash.com/photo-1612927601601-6638404737ce?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 9,
    productCode: 'PROD-009',
    productName: '종가집 포기김치 3kg',
    category: 'BEVERAGE',
    costPrice: 18000.00,
    price: 29000.00,
    description: '전라도 전통 방식으로 담근 포기김치입니다.',
    expirationDate: '2026-04-20',
    leadTime: 5,
    safetyStock: 10,
    unit: 'EA',
    url: 'https://images.unsplash.com/photo-1583224964978-2257b960c3d3?auto=format&fit=crop&q=80&w=400'
  },
  {
    productId: 10,
    productCode: 'PROD-010',
    productName: '우유 식빵 400g',
    category: 'BEVERAGE',
    costPrice: 2500.00,
    price: 3800.00,
    description: '천연 버터를 사용하여 풍미가 좋은 식빵입니다.',
    expirationDate: '2026-01-18',
    leadTime: 1,
    safetyStock: 15,
    unit: 'EA',
    url: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?auto=format&fit=crop&q=80&w=400'
  }
]);

const quantities = ref({});
products.value.forEach(p => {
  quantities.value[p.productId] = 1;
});

const addingToCart = ref({});

const addToCart = async (product) => {
  const qty = quantities.value[product.productId];
  if (qty <= 0) return;

  addingToCart.value[product.productId] = true;
  try {
    const storeId = 1;
    const items = [
      {
        productId: product.productId,
        quantity: qty
      }
    ];
    await cartApi.addCartItem(storeId, items);
    alert(`🛒 ${product.productName} ${qty}개가 장바구니에 담겼습니다!`);
  } catch (err) {
    alert('🔥 장바구니 담기에 실패했습니다.');
  } finally {
    addingToCart.value[product.productId] = false;
  }
};

const goBack = () => {
  router.push('/inventory');
};
</script>

<template>
  <div class="product-list-page">
    <nav class="tteok-nav">
      <div class="nav-container">
        <div class="brand" @click="goBack">
          <span class="brand-icon">🌶️</span>
          <h1 class="brand-name">SPICY <span>ORDER</span></h1>
        </div>
        <div class="nav-actions">
          <button class="cart-link" @click="router.push('/cart')">🛒 장바구니 보기</button>
          <button class="back-link" @click="goBack">◀ 주방으로 돌아가기</button>
        </div>
      </div>
    </nav>

    <header class="tteok-header">
      <div class="header-card premium-card">
        <h2>맛있는 주문하기</h2>
        <p>엄선된 10가지 프리미엄 식재료를 만나보세요.</p>
        <div class="header-decor">📦</div>
      </div>
    </header>

    <main class="main-content">
      <div class="products-grid">
        <div v-for="p in products" :key="p.productId" class="p-card premium-card">
          <div class="p-image-wrapper">
            <img :src="p.url" :alt="p.productName" class="p-image" />
            <div class="p-category">{{ p.category }}</div>
          </div>
          
          <div class="p-info">
            <h3 class="p-name">{{ p.productName }}</h3>
            <p class="p-desc">{{ p.description }}</p>
            <div class="p-meta">
              <span class="p-unit">단위: {{ p.unit }}</span>
              <span class="p-price">₩{{ p.price.toLocaleString() }}</span>
            </div>
          </div>

          <div class="p-actions">
            <div class="qty-selector">
              <button @click="quantities[p.productId] = Math.max(1, quantities[p.productId] - 1)">-</button>
              <input type="number" v-model="quantities[p.productId]" min="1" />
              <button @click="quantities[p.productId]++">+</button>
            </div>
            <button 
              class="btn-spicy add-cart-btn" 
              @click="addToCart(p)"
              :disabled="addingToCart[p.productId]"
            >
              {{ addingToCart[p.productId] ? '담는 중...' : '🛒 장바구니 담기' }}
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.product-list-page {
  min-height: 100vh;
  background-color: #fffbeb;
}

.tteok-nav {
  background-color: #ffffff;
  border-bottom: 4px solid #fde68a;
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.brand {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
}

.brand-icon { font-size: 1.75rem; }
.brand-name { font-size: 1.5rem; font-weight: 900; color: #451a03; margin: 0; }
.brand-name span { color: #e11d48; }

.nav-actions {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}

.cart-link {
  background: #e11d48;
  color: white;
  border: none;
  padding: 0.6rem 1.2rem;
  border-radius: 99px;
  font-weight: 800;
  cursor: pointer;
  box-shadow: 0 4px 0 #9f1239;
  transition: all 0.2s;
}

.cart-link:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 0 #9f1239;
}

.back-link {
  background: none;
  border: none;
  font-weight: 800;
  color: #92400e;
  cursor: pointer;
}

.tteok-header {
  max-width: 1200px;
  margin: 2rem auto 0;
  padding: 0 2rem;
}

.header-card {
  padding: 2.5rem;
  background: white;
  border-radius: 32px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 20px 40px -10px rgba(0,0,0,0.05);
  border: 3px solid #fde68a;
}

.header-card h2 { font-size: 2.25rem; font-weight: 950; color: #451a03; margin: 0 0 0.5rem 0; }
.header-card p { color: #92400e; font-weight: 700; font-size: 1.1rem; }
.header-decor { position: absolute; right: 2rem; bottom: 0.5rem; font-size: 5rem; opacity: 0.1; }

.main-content {
  max-width: 1200px;
  margin: 2rem auto;
  padding: 0 2rem;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2.5rem;
}

.p-card {
  background: white;
  border-radius: 32px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: 0 10px 20px -10px rgba(0,0,0,0.05);
  border: 3px solid #fde68a;
}

.p-image-wrapper {
  height: 200px;
  position: relative;
}

.p-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.p-category {
  position: absolute;
  top: 1rem;
  left: 1rem;
  background: rgba(255,255,255,0.9);
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 800;
  color: #e11d48;
}

.p-info {
  padding: 1.5rem;
  flex: 1;
}

.p-name { font-size: 1.5rem; font-weight: 900; color: #451a03; margin: 0 0 0.5rem 0; }
.p-desc { font-size: 0.95rem; color: #92400e; font-weight: 600; margin-bottom: 1.5rem; line-height: 1.5; }

.p-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: auto;
}

.p-unit { font-size: 0.9rem; font-weight: 700; color: #d0885e; }
.p-price { font-size: 1.25rem; font-weight: 900; color: #e11d48; }

.p-actions {
  padding: 1.5rem;
  background: #fffbeb;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.qty-selector {
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
  border-radius: 14px;
  padding: 0.25rem;
  border: 2px solid #fde68a;
}

.qty-selector button {
  width: 36px;
  height: 36px;
  border: none;
  background: none;
  font-size: 1.5rem;
  font-weight: 900;
  color: #92400e;
  cursor: pointer;
}

.qty-selector input {
  width: 60px;
  border: none;
  text-align: center;
  font-weight: 800;
  font-size: 1.1rem;
  color: #451a03;
}

.btn-spicy {
  background: #e11d48;
  color: white;
  border: none;
  cursor: pointer;
  transition: all 0.2s;
}

.add-cart-btn {
  width: 100%;
  height: 50px;
  border-radius: 14px;
  font-size: 1.05rem;
  font-weight: 800;
  box-shadow: 0 4px 0 #9f1239;
}

.add-cart-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 6px 0 #9f1239;
}

.add-cart-btn:active:not(:disabled) {
  transform: translateY(2px);
  box-shadow: 0 2px 0 #9f1239;
}

.add-cart-btn:disabled {
  background: #fda4af;
  box-shadow: none;
  cursor: not-allowed;
}
</style>
