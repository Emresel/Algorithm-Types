import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os

plt.style.use('seaborn-v0_8-darkgrid')
plt.rcParams['figure.figsize'] = (12, 8)

def plot_sorting_results():
    """Plot sorting algorithm performance"""
    try:
        df = pd.read_csv('sorting_results.csv')
        
        fig, ax = plt.subplots()
        
        for algorithm in df['Algorithm'].unique():
            alg_data = df[df['Algorithm'] == algorithm]
            ax.plot(alg_data['Size'], alg_data['Time(ns)'] / 1e6, 
                   marker='o', label=algorithm, linewidth=2, markersize=6)
        
        ax.set_xlabel('Array Size', fontsize=12, fontweight='bold')
        ax.set_ylabel('Time (ms)', fontsize=12, fontweight='bold')
        ax.set_title('Sorting Algorithm Performance Comparison', fontsize=14, fontweight='bold')
        ax.legend(fontsize=10)
        ax.grid(True, alpha=0.3)
        ax.set_xscale('log')
        ax.set_yscale('log')
        
        plt.tight_layout()
        plt.savefig('sorting_performance.png', dpi=300, bbox_inches='tight')
        print("✓ Generated sorting_performance.png")
        plt.close()
    except Exception as e:
        print(f"✗ Error plotting sorting results: {e}")

def plot_pivot_analysis():
    """Plot Quick Sort pivot analysis"""
    try:
        df = pd.read_csv('pivot_analysis.csv')
        
        fig, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2, figsize=(15, 12))
        
        grouped = df.groupby('Size').agg({
            'Time(ns)': 'mean',
            'PivotCount': 'mean',
            'TimePerPivot': 'mean',
            'PivotsPerSize': 'mean'
        }).reset_index()
        
        ax1.plot(grouped['Size'], grouped['Time(ns)'] / 1e6, 
                marker='o', color='blue', linewidth=2, markersize=6)
        ax1.set_xlabel('Array Size', fontweight='bold')
        ax1.set_ylabel('Time (ms)', fontweight='bold')
        ax1.set_title('Sorting Time vs Array Size', fontweight='bold')
        ax1.set_xscale('log')
        ax1.set_yscale('log')
        ax1.grid(True, alpha=0.3)
        
        ax2.plot(grouped['Size'], grouped['PivotCount'], 
                marker='s', color='red', linewidth=2, markersize=6)
        ax2.set_xlabel('Array Size', fontweight='bold')
        ax2.set_ylabel('Pivot Count', fontweight='bold')
        ax2.set_title('Pivot Count vs Array Size', fontweight='bold')
        ax2.set_xscale('log')
        ax2.set_yscale('log')
        ax2.grid(True, alpha=0.3)
        
        ax3.scatter(df['PivotCount'], df['Time(ns)'] / 1e6, 
                   alpha=0.5, s=20, color='green')
        ax3.set_xlabel('Pivot Count', fontweight='bold')
        ax3.set_ylabel('Time (ms)', fontweight='bold')
        ax3.set_title('Time vs Pivot Count', fontweight='bold')
        ax3.grid(True, alpha=0.3)
        
        ax4.plot(grouped['Size'], grouped['PivotsPerSize'], 
                marker='^', color='purple', linewidth=2, markersize=6)
        ax4.set_xlabel('Array Size', fontweight='bold')
        ax4.set_ylabel('Pivots per Size', fontweight='bold')
        ax4.set_title('Pivots per Size Ratio', fontweight='bold')
        ax4.set_xscale('log')
        ax4.grid(True, alpha=0.3)
        
        plt.tight_layout()
        plt.savefig('pivot_analysis.png', dpi=300, bbox_inches='tight')
        print("✓ Generated pivot_analysis.png")
        plt.close()
    except Exception as e:
        print(f"✗ Error plotting pivot analysis: {e}")

def plot_tree_insertion():
    """Plot tree insertion performance"""
    try:
        df = pd.read_csv('tree_insertion_results.csv')
        
        fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(16, 6))
        
        for tree_type in df['TreeType'].unique():
            tree_data = df[df['TreeType'] == tree_type]
            grouped = tree_data.groupby(['Size', 'Order']).agg({
                'Time(ns)': 'mean',
                'Height': 'mean'
            }).reset_index()
            
            for order in grouped['Order'].unique():
                order_data = grouped[grouped['Order'] == order]
                label = f"{tree_type} ({order})"
                ax1.plot(order_data['Size'], order_data['Time(ns)'] / 1e6, 
                        marker='o', label=label, linewidth=2, markersize=5)
                ax2.plot(order_data['Size'], order_data['Height'], 
                        marker='s', label=label, linewidth=2, markersize=5)
        
        ax1.set_xlabel('Tree Size (2^m - 1)', fontweight='bold')
        ax1.set_ylabel('Insertion Time (ms)', fontweight='bold')
        ax1.set_title('Tree Insertion Performance', fontweight='bold')
        ax1.legend(fontsize=8, loc='best')
        ax1.set_xscale('log')
        ax1.set_yscale('log')
        ax1.grid(True, alpha=0.3)
        
        ax2.set_xlabel('Tree Size (2^m - 1)', fontweight='bold')
        ax2.set_ylabel('Tree Height', fontweight='bold')
        ax2.set_title('Tree Height vs Size', fontweight='bold')
        ax2.legend(fontsize=8, loc='best')
        ax2.set_xscale('log')
        ax2.grid(True, alpha=0.3)
        
        plt.tight_layout()
        plt.savefig('tree_performance.png', dpi=300, bbox_inches='tight')
        print("✓ Generated tree_performance.png")
        plt.close()
    except Exception as e:
        print(f"✗ Error plotting tree performance: {e}")

def plot_tree_removal():
    """Plot tree removal performance"""
    try:
        df = pd.read_csv('tree_removal_results.csv')
        
        fig, ax = plt.subplots(figsize=(12, 8))
        
        for tree_type in df['TreeType'].unique():
            tree_data = df[df['TreeType'] == tree_type]
            grouped = tree_data.groupby('Size').agg({
                'Time(ns)': 'mean'
            }).reset_index()
            
            ax.plot(grouped['Size'], grouped['Time(ns)'] / 1e6, 
                   marker='o', label=tree_type, linewidth=2, markersize=6)
        
        ax.set_xlabel('Tree Size', fontweight='bold')
        ax.set_ylabel('Removal Time (ms)', fontweight='bold')
        ax.set_title('Tree Removal Performance Comparison', fontweight='bold')
        ax.legend(fontsize=10)
        ax.set_xscale('log')
        ax.set_yscale('log')
        ax.grid(True, alpha=0.3)
        
        plt.tight_layout()
        plt.savefig('tree_removal_performance.png', dpi=300, bbox_inches='tight')
        print("✓ Generated tree_removal_performance.png")
        plt.close()
    except Exception as e:
        print(f"✗ Error plotting tree removal: {e}")

if __name__ == "__main__":
    print("Generating graphs from CSV files...")
    print("=" * 50)
    
    if not os.path.exists('sorting_results.csv'):
        print("⚠ Warning: sorting_results.csv not found. Run assignment1task1partA.java first.")
    else:
        plot_sorting_results()
    
    if not os.path.exists('pivot_analysis.csv'):
        print("⚠ Warning: pivot_analysis.csv not found. Run assignment1task3.java first.")
    else:
        plot_pivot_analysis()
    
    if not os.path.exists('tree_insertion_results.csv'):
        print("⚠ Warning: tree_insertion_results.csv not found. Run assignment2task1and2and3.java first.")
    else:
        plot_tree_insertion()
    
    if not os.path.exists('tree_removal_results.csv'):
        print("⚠ Warning: tree_removal_results.csv not found. Run assignment2task1and2and3.java first.")
    else:
        plot_tree_removal()
    
    print("=" * 50)
    print("Graph generation complete!")
    print("\nGenerated files:")
    print("  - sorting_performance.png")
    print("  - pivot_analysis.png")
    print("  - tree_performance.png")
    print("  - tree_removal_performance.png")
    print("\nInclude these graphs in your report!")
